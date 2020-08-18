package data.dao;

import data.DataException;
import data.impl.SondaggioImpl;

public interface SondaggioDAO {

	SondaggioImpl getSondaggio(int sondaggio_id) throws DataException;

}
