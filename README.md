# UserDataFeatureAdvanceProject

All Class Diagrams  : https://www.figma.com/file/VU8NKNLFG8kMyehBwOmyJO/All-Digrams?type=whiteboard&node-id=0-1&t=buyHSrZNf7J4z42M-0

**Advanced User Data Feature - Overview:**

The advanced "User Data" feature in the Java system is meticulously crafted to elevate the user experience within programs akin to social networking sites. Its primary focus revolves around furnishing efficient and advanced capabilities for data management, playing an integral role in fortifying the core system. This feature empowers users, affording them comprehensive and effective control over all their data within the program.

**"data" File:**
- The source code for this feature is conveniently encapsulated within a file named "data."

**Export and Deletion Management:**

*Empowering users with seamless data management, the feature introduces enhanced export and deletion options, thoughtfully tailored to individual preferences.*

- **Data Export Options:**
  Users are endowed with the flexibility to dictate the manner in which they download their data directly from the program. The export process allows users to opt for either downloading individual PDF files containing their user data or selecting a compressed file housing all PDF files.

**User Categories and Export Specifications:**

**For New Users:**
- Export IAM Data.
- Export Posts.

**For Regular Users:**
- Export IAM Data.
- Export Posts.
- Export Activities.

**For Premium Users:**
- Export all data types, including IAM, Posts, Activities, and Payment Information.

  
- **Deletion Options:**
  The feature introduces two deletion methodologies, offering users tailored control over their data:

1. **Hard Delete:**
   - Users can permanently expunge their entire account. Following a hard delete, the user is precluded from recreating an account with the same username in the future.

2. **Soft Delete:**
   - Empowering users with the ability to selectively remove specific types of data from their profile, this option enables users to decide which data categories they wish to delete.

**Design Patterns Utilized:**
1. **Singleton Pattern**
2. **Strategy Pattern**
3. **Decorator Pattern**
4. **Factory Pattern**
5. **In addition, the Application of SOLID Principles has been meticulously applied.**

*This meticulously designed feature aims to augment user privacy, delivering effective data management within a secure, standards-compliant environment.*
