package edu.najah.cap.data.Delete;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import edu.najah.cap.data.LoggerSetup;
import edu.najah.cap.data.MergeObject;
import edu.najah.cap.exceptions.SystemBusyException;
import edu.najah.cap.posts.IPostService;
import edu.najah.cap.posts.Post;
public class DeletePosts implements Deletion {
    private static final Logger logger = LoggerSetup.getLogger(); 
    private IPostService postService;

    public DeletePosts(IPostService postService) {
        this.postService = postService;
        logger.info("DeletePosts initialized with IPostService.");
    }

    private boolean tryDeletePost(String userName, String postId) {
        final int maxRetries = 3; 
        final long delayMillis = 1000; 

        for (int i = 0; i < maxRetries; i++) {
            try {
                postService.deletePost(userName, postId);
                logger.info("Post " + postId + " deleted for user: " + userName);
                return true;
            } catch (SystemBusyException e) {
                logger.warning("SystemBusyException encountered. Retrying to delete post: " + postId + " for user: " + userName);
                System.out.println("Waiting...");
                try {
                    Thread.sleep(delayMillis);
                } catch (InterruptedException ie) {
                    logger.warning("Thread interrupted while waiting to retry deletion of post: " + postId);
                }
            } catch (Exception e) {
                logger.warning("Error occurred while trying to delete post: " + postId + " for user: " + userName);
                logger.warning(e.toString());
                return false;
            }
        }
        logger.warning("Failed to delete post: " + postId + " for user: " + userName + " after max retries.");
        return false; 
    }

    @Override
    public boolean removeData(String userName, MergeObject mergeObject) {
        boolean isRemovedSuccessfully = false;
        try {
            List<Post> userPosts = postService.getPosts(userName);
            logger.info("Retrieved posts for user: " + userName + ". Number of posts: " + userPosts.size());

            for (Post post : new ArrayList<>(userPosts)) {
                if (!tryDeletePost(userName, post.getId())) {
                    logger.warning("Failed to delete post: " + post.getId() + " for user: " + userName);
                }
            }

            userPosts = postService.getPosts(userName);
            logger.info("Post deletion process completed. Remaining posts for user: " + userName + ": " + userPosts.size());

            mergeObject.setPosts(userPosts);

            if (userPosts.isEmpty()) {
                logger.info("All posts successfully deleted for user: " + userName);
                isRemovedSuccessfully = true;
            } else {
                logger.info("Some posts were not deleted for user: " + userName);
            }
            System.out.println("Deletion process completed successfully");
        } catch (Exception e) {
            logger.warning("Error occurred while removing posts for user: " + userName);
            logger.warning(e.getMessage());
        }
        return isRemovedSuccessfully;
    }
}
