package com.subcircle.services.support;

import java.sql.PreparedStatement;

import com.subcircle.system.db.DBUtils;

class PreparedStatementMetaData
{

	private PreparedStatement pstm=null;
	private boolean isBatch;
	
	public PreparedStatementMetaData(PreparedStatement pstm,boolean isBatch)
	{
		this.pstm=pstm;
		this.isBatch=isBatch;
	}
	
	public void executePreparedStatement()throws Exception
	{
		if(isBatch)
		{
			pstm.executeBatch();
		}
		else
		{
			pstm.executeUpdate();
		}
	}
	
	public void close()
	{
		DBUtils.close(pstm);
	}

}
