package sharedRegion;

import entities.Chef;

public class Kitchen {

	private final Chef chef;
	private final GeneralRepo repos;

	public Kitchen(GeneralRepo repos) {
		this.chef = null;
		this.repos = repos;
	}

}
