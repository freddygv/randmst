APP = RandMST

SRC = src/cs124/mst/*.java
CLASS_PATH = bin/

JAVAC = javac
JAR_CMD = jar cvfm
JAR_NAME = RandMST.jar
MANIFEST = MAIN_MANIFEST.MF


$(APP): $(SRC)
	mkdir -p $(CLASS_PATH)
	$(JAVAC) -classpath $(CLASS_PATH) -d $(CLASS_PATH) $(SRC)
	$(JAR_CMD) $(JAR_NAME) $(MANIFEST) -C $(CLASS_PATH) .

clean:
	rm -rf $(CLASS_PATH) *.jar
