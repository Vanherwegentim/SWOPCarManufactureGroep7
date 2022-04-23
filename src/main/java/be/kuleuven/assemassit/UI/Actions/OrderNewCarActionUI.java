public void run() {
    while (true) {

      Map<Integer, String> allWorkPosts = performAssemblyTasksController.giveAllWorkPosts();
      Optional<Integer> chosenWorkPostIdOptional = displayChooseWorkPost(allWorkPosts);

      if (chosenWorkPostIdOptional.isEmpty()) continue; // TODO: show feedback

      int chosenWorkPostId = chosenWorkPostIdOptional.get();

      Map<Integer, String> allAssemblyTasks = performAssemblyTasksController.givePendingAssemblyTasks(chosenWorkPostId);

      if (allAssemblyTasks.isEmpty()) {
        System.out.println("There are currently no pending tasks for this work post");
        continue;
      }

      Optional<Integer> chosenAssemblyTaskIdOptional = displayChooseAssemblyTask(allAssemblyTasks);

      if (chosenAssemblyTaskIdOptional.isEmpty()) continue; // TODO: show feedback

      int chosenAssemblyTaskId = chosenAssemblyTaskIdOptional.get();

      performAssemblyTasksController.setActiveTask(chosenWorkPostId, chosenAssemblyTaskId);

      List<String> actions = performAssemblyTasksController.giveAssemblyTaskActions(chosenWorkPostId, chosenAssemblyTaskId);

      System.out.println();
      System.out.println("Execute the following actions:");
      displayActions(actions);

      int duration = displayInputMinutes();
      performAssemblyTasksController.completeAssemblyTask(chosenWorkPostId, duration);

      this.carMechanicActionsOverviewUI.run();

      break; // if we reach this point, the use case is done, java call stack will now return to the previous UI
    }
  }