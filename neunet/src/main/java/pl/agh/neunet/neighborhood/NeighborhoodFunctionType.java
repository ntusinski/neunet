package pl.agh.neunet.neighborhood;

public enum NeighborhoodFunctionType {
	CONTEST(new ContestNeighborhoodFunction()), //
	NEIGHBORHOOD_1D(new Neighborhood1DFunction()), //
	NEIGHBORHOOD_2D(new Neighborhood2DFunction()); //

	private NeighborhoodFunction function;

	private NeighborhoodFunctionType(NeighborhoodFunction function) {
		this.function = function;
	}

	public NeighborhoodFunction getNeighborhoodFunction() {
		return function;
	}
}
