-include ../../Makefile.rules

# Target Package
TARGET=Models

# Files to compile in this package
CLASSES=                      \
        Adapters.java         \
        Alerts.java           \
        CommandBuilder.java   \
        Load.java             \
        OutputParameters.java \
        Save.java             \
        Tuple.java            \
        Utils.java

# JARs required for compiling this package
CLASSPATH_JARS=javafx-base.jar     \
               javafx-controls.jar \
               javafx-fxml.jar     \
               javafx-graphics.jar \
               gson.jar

#               javafx-media.jar    \
#               javafx-swing.jar    \
#               javafx-web.jar

# Build CLASSPATH for this package
empty:=
space:= $(empty) $(empty)
CLASSPATH:=$(addprefix $(CLASSPATH_LIB)/,$(CLASSPATH_JARS))
CLASSPATH:=$(subst $(space),$(CLASSPATH_SEP),$(CLASSPATH))
CLASSPATH:=$(addsuffix $(CLASSPATH),$(CLASSPATH_BIN)$(CLASSPATH_SEP))
CLASSPATH:=$(addsuffix $(CLASSPATH),..$(CLASSPATH_SEP))

#
# Clear any default targets for building .class files from .java files; we
# will provide our own target entry to do this in this makefile.
# make has a set of default targets for different suffixes (like .c.o)
# Currently, clearing the default for .java.class is not necessary since
# make does not have a definition for this target, but later versions of
# make may, so it doesn't hurt to make sure that we clear any default
# definitions for these
#

.SUFFIXES: .java .class


#
# Here is our target entry for creating .class files from .java files
# This is a target entry that uses the suffix rule syntax:
#	DSTS:
#		rule
#  'TS' is the suffix of the target file, 'DS' is the suffix of the dependency
#  file, and 'rule'  is the rule for building a target
# '$*' is a built-in macro that gets the basename of the current target
# Remember that there must be a < tab > before the command line ('rule')
#

.java.class:
	$(JC) $(JFLAGS) $<

#
# the default make target entry
#

default: classes


#
# This target entry uses Suffix Replacement within a macro:
# $(name:string1=string2)
# 	In the words in the macro named 'name' replace 'string1' with 'string2'
# Below we are replacing the suffix .java of all words in the macro CLASSES
# with the .class suffix
#

classes: $(CLASSES:.java=.class)


#
# RM is a predefined macro in make (RM = rm -f)
#

clean:
	$(RM) *.class
	$(RM) *~
	$(RM) *#*
