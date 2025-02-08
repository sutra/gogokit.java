package org.oxerr.viagogo.client.cached.inventory;

public interface CheckOptions {

	static CheckOptions defaults() {
		return new CheckParams();
	}

	CheckOptions pageSize(int pageSize);

	CheckOptions chunkSize(int chunkSize);

	int pageSize();

	int chunkSize();

}
