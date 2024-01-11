package edu.najah.cap.data;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import edu.najah.cap.activity.IUserActivityService;
import edu.najah.cap.exceptions.BadRequestException;
import edu.najah.cap.exceptions.NotFoundException;
import edu.najah.cap.exceptions.SystemBusyException;
import edu.najah.cap.iam.IUserService;
import edu.najah.cap.iam.UserProfile;
import edu.najah.cap.iam.UserType;
import edu.najah.cap.payment.IPayment;
import edu.najah.cap.posts.IPostService;
import edu.najah.cap.posts.Post;

public class DataFacadeImpl implements DataFacade {
    private static final Logger logger = LoggerSetup.getLogger();

static {
    try {
        FileHandler fileHandler = new FileHandler("output.log");
        SimpleFormatter formatter = new SimpleFormatter();
        fileHandler.setFormatter(formatter);
        logger.setUseParentHandlers(false);
        logger.addHandler(fileHandler);
    } catch (IOException e) {
        logger.log(Level.SEVERE, "An error occurred while configuring the file handler", e);
    }
}

    private final IUserService userService;
    private final IPostService postService;
    private final IPayment paymentService;
    private final IUserActivityService userActivityService;
    private final Map<UserType, MergeObject> mergeObjectMap = new HashMap<>();
    private Map<UserType, UserTypeStrategy> strategyMap = new HashMap<>();
    public static final int MAX_ATTEMPTS = 5;
    public static final int RETRY_DELAY = 1000;

    public DataFacadeImpl(IUserService userService, IPostService postService, IPayment paymentService,
                          IUserActivityService userActivityService) {
        this.userService = userService;
        this.postService = postService;
        this.paymentService = paymentService;
        this.userActivityService = userActivityService;
        initializeStrategyMap();
    }

    private void initializeStrategyMap() {
        strategyMap.put(UserType.PREMIUM_USER, new PremiumUserStrategy(paymentService, userActivityService));
        strategyMap.put(UserType.REGULAR_USER, new RegularUserStrategy(userActivityService));
        strategyMap.put(UserType.NEW_USER, new NewUserStrategy());
    }

    @Override
    public MergeObject getMergedData(String userName) {
        MergeObject mergeObject = new MergeObject();
        boolean successful = false;
        int attempts = 0;
        while (!successful && attempts < MAX_ATTEMPTS) {
            try {
                UserProfile userProfile = userService.getUser(userName);
                if (userProfile == null) {
                    logger.warning("User profile for " + userName + " is null.");
                    return null;
                }
                List<Post> posts = postService.getPosts(userName);
                mergeObject.setUserProfile(userProfile);
                mergeObject.setPosts(posts);

                UserType userType = userProfile.getUserType();
                UserTypeStrategy strategy = strategyMap.get(userType);
                if (strategy == null) {
                    logger.warning("Strategy for user type " + userType + " is null.");
                    return null;
                }
                strategy.collectUserData(mergeObject, userName);

                mergeObjectMap.put(userType, mergeObject);
                successful = true;
            } catch (SystemBusyException e) {
                attempts++;
                logger.warning("System is busy, retrying... (" + attempts + ")");

                try {
                    Thread.sleep(RETRY_DELAY);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            } catch (NotFoundException | BadRequestException e) {
                logger.warning("Exception occurred while fetching data for user: " + userName);
                logger.warning(e.getMessage());
                return null;
            }
        }
        if (!successful) {
            logger.warning("Failed to get user profile after " + attempts + " attempts.");
            return null;
        }
        return mergeObject;
    }

    @Override
    public Map<UserType, MergeObject> getMergeObjectMap() {
        return mergeObjectMap;
    }
}
