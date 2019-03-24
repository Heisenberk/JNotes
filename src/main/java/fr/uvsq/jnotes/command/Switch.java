package fr.uvsq.jnotes.command;

import java.util.List;
import java.util.ArrayList;

public class Switch {

   private List<Command> history = new ArrayList<Command>();

   public Switch() {
   }

   public void storeAndExecute(Command cmd) {
      this.history.add(cmd); // optional 
      cmd.execute();        
   }
}