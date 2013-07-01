package pl.agh.neunet.kohonen;

public enum NeighborhoodFunctionType {
	CONTEST(new ContestNeighborhoodFunction()); //

	private NeighborhoodFunction function;

	private NeighborhoodFunctionType(NeighborhoodFunction function) {
		this.function = function;
	}

	public NeighborhoodFunction getNeighborhoodFunction() {
		return function;
	}
}
