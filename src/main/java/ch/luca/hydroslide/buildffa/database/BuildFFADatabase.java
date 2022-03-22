package ch.luca.hydroslide.buildffa.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import ch.luca.hydroslide.buildffa.BuildFFA;
import lombok.Getter;

public class BuildFFADatabase {

	@Getter
	private ExecutorService executor = Executors.newCachedThreadPool();
	
	public void executeQuery(String statement, boolean async, Consumer<ResultSet> consumer) {
		if(!async) {
			Connection connection = null;
			try {
				connection = BuildFFA.getInstance().getSqlPool().getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(statement);
				ResultSet resultSet = preparedStatement.executeQuery();
				consumer.accept(resultSet);
				preparedStatement.close();
				resultSet.close();
			} catch(SQLException e) {
				e.printStackTrace();
			} finally {
				if(connection != null) {
					try {
						connection.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			return;
		}
		executor.execute(() -> {
			Connection connection = null;
			try {
				connection = BuildFFA.getInstance().getSqlPool().getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(statement);
				ResultSet resultSet = preparedStatement.executeQuery();
				consumer.accept(resultSet);
				preparedStatement.close();
				resultSet.close();
			} catch(SQLException e) {
				e.printStackTrace();
			} finally {
				if(connection != null) {
					try {
						connection.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
	}
	public void update(String statement, boolean async) {
		if(!async) {
			Connection connection = null;
			try {
				connection = BuildFFA.getInstance().getSqlPool().getConnection();
				
				PreparedStatement preparedStatement = connection.prepareStatement(statement);
				preparedStatement.executeUpdate();
				preparedStatement.close();
			} catch(SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					if(connection != null) {
						connection.close();
					}
				} catch(Exception exc) {
					exc.printStackTrace();
				}
			}
			return;
		}
		executor.execute(() -> {
			Connection connection = null;
			try {
				connection = BuildFFA.getInstance().getSqlPool().getConnection();
				
				PreparedStatement preparedStatement = connection.prepareStatement(statement);
				preparedStatement.executeUpdate();
				preparedStatement.close();
			} catch(SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					if(connection != null) {
						connection.close();
					}
				} catch(Exception exc) {
					exc.printStackTrace();
				}
			}
		});
	}
	public int getRank(String uuid) {
		int rank = 0;
		boolean finish = false;
		Connection connection = null;
		try {
			connection = BuildFFA.getInstance().getSqlPool().getConnection();
			
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT uuid FROM BuildFFA ORDER BY kills DESC");
			ResultSet rs = preparedStatement.executeQuery();
			while((rs.next()) && (!finish)) {
				rank++;
				if(rs.getString("uuid").equals(uuid)) {
					finish = true;
				}
			}
			preparedStatement.close();
			rs.close();
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(connection != null) {
					connection.close();
				}
			} catch(Exception exc) {
				exc.printStackTrace();
			}
		}
		return rank;
	}
}
