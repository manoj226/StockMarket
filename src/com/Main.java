

package com;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.drools.RuleBase;
import org.drools.RuleBaseFactory;
import org.drools.StatefulSession;
import org.drools.compiler.DroolsParserException;
import org.drools.compiler.PackageBuilder;
import org.drools.compiler.PackageBuilderErrors;

/**
* Basic Hello World Program using Drools 5.1
* @author vishal akhouri
*/

public class Main {
private static RuleBase rbase = RuleBaseFactory.newRuleBase();;
private static PackageBuilder pbuilder = new PackageBuilder();
private static StatefulSession sessionObject;
private static String drl = "/com/messaging/Rules1.drl";

/**
* The Main method
*
* @param args the command line arguments
*/

public static void main(String[] args) {
initialiseDrools();
initiliseMessageObject();
runRules();
}

// Method to initialise the package  builder and add package to the rule base.
private static void initialiseDrools() {
//1. Read the DRL File and add to package builder
try {
Reader reader = new InputStreamReader(Main.class.getResourceAsStream("/com/Brule.drl"));
pbuilder.addPackageFromDrl(reader);
} catch (DroolsParserException ex) {
Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
} catch (IOException ex) {
Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
}

//2. Check for any errors
PackageBuilderErrors errors = pbuilder.getErrors();

if (errors.getErrors().length > 0) {
System.out.println("Some errors exists in packageBuilder");
for (int i = 0; i < errors.getErrors().length; i++) {
System.out.println(errors.getErrors()[i]);
}
throw new IllegalArgumentException("Could not parse knowledge.");
}

//3. Add package to rule base
try {
rbase.addPackage(pbuilder.getPackage());
} catch (Exception e) {
System.out.println("Error: "+ e);
}
}

// Method to insert message object in session
private static void initiliseMessageObject() {
mespojo msg = new mespojo();
msg.setType("Hello");
sessionObject = rbase.newStatefulSession();
sessionObject.insert(msg);
}

// Method to fire all rules
private static void runRules() {
sessionObject.fireAllRules();
}
}