-include Makefile.rules

TARGET=PipeCrafter-JavaFX
DIST_FOLDER=$(TARGET)
MANIFEST=Manifest.mf

SRC_DIRS=App 		\
	Execution       \
	Models          \
	Views           \
	Utils           \
	Business

OTHER_RESOURCES=src/main/resources/css \
	src/main/resources/fxml        \
	src/main/resources/images

JARFILE=$(TARGET).jar

all: $(JARFILE)

# Call Make for each folder containing sources
compile:
	$(MAKE) $(SRC_DIRS)

$(TARGET): compile

# Compile sources & copy resources & Create a JAR file
jar: compile supplements
#	(cd bin && \
#	  $(JAR) --create --file=$(JARFILE) --manifest=../$(MANIFEST) ./*)
	(cd bin && $(JAR) -cvfm $(JARFILE) ../$(MANIFEST) ./*)
	mv -f ./bin/$(JARFILE) ./

$(JARFILE): jar

# Go in each folder to compile & move each file to "bin" folder
$(SRC_DIRS):
	(cd source/$@ && $(MAKE))
	mkdir -p ./bin/$@
	mv ./source/$@/*.class ./bin/$@

# Call Make for each supplement folder containing resources
supplements:
	$(MAKE) $(OTHER_RESOURCES)

# Copy each supplement into the "bin" folder
$(OTHER_RESOURCES):
	cp -r $@ ./bin

# Clean building files
clean:
	rm -f *~
	rm -rf *#*
	find . -name "*~" -exec rm -rf '{}' \;
	find . -name "*#*" -exec rm -rf '{}' \;
	find . -name "*.class" -exec rm -rf '{}' \;
	rm -f bin/$(TARGET).jar
	rm -rf bin/

# Clean binaries & configure
distclean: clean
	rm -f $(TARGET).jar
	rm -rf $(DIST_FOLDER)
	rm -f $(DIST_FOLDER).tar.bz2
	rm -f Makefile.rules
	rm -rf Output/

# Create the tarball by copying files and deleting .svn directories
dist: distclean
	sh configure
	mkdir -p $(DIST_FOLDER)
	cp -r configure Makefile $(DIST_FOLDER)
	cp -r $(MANIFEST) JARGeneration.jardesc pom.xml $(DIST_FOLDER)
	cp -r --parents $(OTHER_RESOURCES) $(DIST_FOLDER)
	cp -r test $(DIST_FOLDER)
	cp -r source $(DIST_FOLDER)
	find $(DIST_FOLDER) -depth -name ".svn" -type d -exec rm -rf '{}' \;
	find $(DIST_FOLDER) -depth -name ".git" -type d -exec rm -rf '{}' \;
	tar cvjf $(DIST_FOLDER).tar.bz2 $(DIST_FOLDER)
	rm -rf $(DIST_FOLDER)
	$(MD5) $(DIST_FOLDER).tar.bz2

.PHONY: $(OTHER_RESOURCES)


### Obsolete (creation of JARfile without resources)
old_jar: compile
	(cd bin && $(JAR) -cvmf ../$(MANIFEST) $(JARFILE) $(SRC_DIRS))
	mv -f ./bin/$(JARFILE) ./
