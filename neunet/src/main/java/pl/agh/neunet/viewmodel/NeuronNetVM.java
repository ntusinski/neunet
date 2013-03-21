package pl.agh.neunet.viewmodel;

public interface NeuronNetVM {
	void registerModelObserver(ModelObserver observer);

	void modelChanged();
}
