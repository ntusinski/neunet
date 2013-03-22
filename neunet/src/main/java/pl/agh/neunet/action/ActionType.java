package pl.agh.neunet.action;

public enum ActionType {
	CREATE_DEFAULT_PROPERTIES_FILE(ActionGroup.PROPERTIES_FILE), //
	LOAD_PROPERTIES_FILE(ActionGroup.PROPERTIES_FILE); //

	private ActionGroup group;

	private ActionType(ActionGroup group) {
		this.group = group;
	}

	public ActionGroup getGroup() {
		return group;
	}
}
