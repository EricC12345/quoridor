Quoridor Project Help and Tips

Gradle Help
===========

    1. If you have never run gradle, you might have to install it. run:

                ./gradlew


    2. It should say downloading gradle..................
         afterward you can run 

                ./gradlew build 
                ./gradlew clean


         The first builds the whole project (including running any tests),
         the second removes the build.


    3. After you have built the code, run the following:

                java -cp build/libs/quoridorschmoridor.jar Main


         for an example of running a program.

Tips
====

    1. Easy gradle build function

        add this to your .bashrc or .zshrc :

                function gb() {
                    pushd . > /dev/null
                    # you might have to adjust this line below
                    cd "$HOME/405/teams/" > /dev/null
                    ./gradlew build "$@"
                    popd > /dev/null
                }


        you can now type gb from any directory to build the project!
          also you can pass arguments to gb like this: gb clean


    2. Run class files from anywhere

        this will allow you to run any of your class files for our project
          regardless of what directory you are currently in

                export CLASSPATH="$HOME/teams/build/classes/main$CLASSPATH"


    3. better git log
        
        configure git alias:
        
            git config --global alias.lg "log --color --graph --pretty=format:'%Cred%h%Creset -%C(yellow)%d%Creset %s %Cgreen(%cr) %C(bold blue)<%an>%Creset' --abbrev-commit"

        then 

            git lg


    4. gitk


    5. other gradle build options

            gb javadoc
                outputs html files in build/docs/javadoc/

            gb jar
                does exactly what it says on the tin (uh...)


    6. git shortlog -ns (thanks matt)

            prints a list of each member's name along with their number of commits
