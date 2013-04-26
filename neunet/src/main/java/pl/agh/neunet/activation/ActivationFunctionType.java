package pl.agh.neunet.activation;

public enum ActivationFunctionType {
	LINEAR(new LinearActivationFunction()), //
	STEP(new StepActivationFunction()), //
	SIGMOIDAL(new SigmoidalActivationFunction()); //

	private ActivationFunction activationFunction;

	private ActivationFunctionType(ActivationFunction activationFunction) {
		this.activationFunction = activationFunction;
	}

	public ActivationFunction getActivationFunction() {
		return activationFunction;
	}
}
