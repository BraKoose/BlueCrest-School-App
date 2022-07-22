# BlueCrest-School-App

I am rebuilding the old Bluecrest App. 
Using at most 2Weeeks

I am rebuilding his old project and these were my fixes
1. I upgrade the build.Gradle, including the dependencies

2. I changed the FindViewByID to view binding instead. To avoid calling the R so much. To reduce memory usage.
View Binding is Google's new method of referencing views. It generates a binding class for each of your XML layout files and is used to interact with all views that have an assigned id value in a null-safe and type-safe way.

3. I Removed a lot of non-null assertions. eg. In All the modules Prince - use " privatevar mAuth?: FirebaseAuth = null" outside the myclassActivity: AppCompatActivity() class. Which is bad. but instead, I use
"private lateinit var mAuth: FirebaseAuth" in the myclassActivity: AppCompatActivity() class and remove !! or ? which express null in the var.

The most significant advantage of using this keyword is that it does not allocate any memory to the variable before it is initialized.
4. Changing Hard Coded string to String Values and some other petty fixes.

#100daysofgads Day 1
I learned Tools and Testing in Android Studio, Where I applied the knowledge in housekeeping an old app, I am making the kotlin more robust.

Source code BRANCH [House Keeping] - https://lnkd.in/dK-M_Z7f

![](https://github.com/BraKoose/BlueCrest-School-App/blob/master/bluecrest.jpeg)

Day 2
I learned Advance Constraint Layout, and Resources, Themeing App. I applied that knowledge and built an intro page. and clicks to log in to signUp. I have already login into the app.
I am working on the Logins Pages. on Day3
#Current Design 
source code - https://lnkd.in/duzmaS_i
![](https://github.com/BraKoose/BlueCrest-School-App/blob/master/bluecrest.jpg)



