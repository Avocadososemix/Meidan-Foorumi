package tikape.runko.database;

import java.sql.*;
import java.util.*;

public interface Dao<T, K> {

    List<T> etsiKaikki() throws SQLException;
    void tallenna(T Element)throws SQLException;
    List<T> etsiTietyt (K key) throws SQLException;


}
