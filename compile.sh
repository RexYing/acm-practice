    #!/bin/sh  

    # Define some constants  
    PROJECT_PATH=acm-practice
    BIN_PATH=bin
    SRC_PATH=src
      
    # First remove the sources.list file if it exists and then create the sources file of the project  
    rm -f $SRC_PATH/sources  
    find $SRC_PATH -name *.java > $SRC_PATH/sources.list  
      
    # Compile the project  
    #javac -d $BIN_PATH -classpath $JAR_PATH1/jxl.jar:$JAR_PATH2/jfreechart-1.0.14-demo.jar @$SRC_PATH/sources.list  

    javac -d $BIN_PATH @$SRC_PATH/sources.list 
