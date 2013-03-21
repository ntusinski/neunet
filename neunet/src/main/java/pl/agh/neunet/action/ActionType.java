package pl.agh.neunet.action;

public enum ActionType {
	CREATE_DEFAULT_PROPERTY_FILE(ActionGroup.PROPERTY_FILE);

	private ActionGroup group;

	private ActionType(ActionGroup group) {
		this.group = group;
	}

	public ActionGroup getGroup() {
		return group;
	}
}
