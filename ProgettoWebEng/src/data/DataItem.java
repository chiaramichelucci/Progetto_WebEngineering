package data;

public interface DataItem<KT> {
	
	KT getKey();

    long getVersion();

    void setKey(KT key);

    void setVersion(long version);
}
