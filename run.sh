    #!/bin/sh  
      
    # Define some constants  
    PROJECT_PATH=acm-practice
    #BIN_PATH=$PROJECT_PATH/bin
    BIN_PATH=bin
    ENTRANCE=Main
    echo "main entrance?"
    read ENTRANCE
      
    # Run the project
    cd $BIN_PATH
    java $ENTRANCE
