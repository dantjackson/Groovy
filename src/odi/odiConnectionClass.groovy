// Author: Rittman Mead
//
// Class : OdiConnection
// Change :
// Desc : Establishes and ODI connection, returns ODI params for connection and connection instance. 

package odi

import groovy.beans.Bindable;
import oracle.odi.core.OdiInstance;
import oracle.odi.core.config.MasterRepositoryDbInfo;
import oracle.odi.core.config.OdiInstanceConfig;
import oracle.odi.core.config.PoolingAttributes;
import oracle.odi.core.config.WorkRepositoryDbInfo;
import oracle.odi.core.exception.OdiRuntimeException;
import oracle.odi.core.persistence.transaction.ITransactionStatus;
import oracle.odi.core.persistence.transaction.support.DefaultTransactionDefinition;
import oracle.odi.core.security.Authentication;

@Bindable
class OdiConnection {

	OdiInstance odiInstance
	OdiParams odiparams
	Authentication auth
	String url, drv, db_usr, db_pass, odi_usr, odi_pass, work_rep
	String toString() {
		"OdiConnection[url=$url,drv=$drv,db_usr=$db_usr,db_pass=$db_pass,odi_usr=$odi_usr,odi_pass=$odi_pass,db_usr=$db_usr,work_rep=$work_rep]"
	}

	def odiConnect (String configPath) {

		// Slurp Up Params
		odiparams = new OdiParams();

		// Need to make this a relative path
		odiparams.loadParams(configPath, 'interface.conf', 'connection.conf');

		// Get params from Slurper Class
		this.url = odiparams.getUrl();
		this.drv = odiparams.getDriver();
		this.db_usr = odiparams.getMasterUser();
		this.db_pass = odiparams.getMasterPass();
		this.odi_usr = odiparams.getOdiUser();
		this.odi_pass = odiparams.getOdiPass();
		this.work_rep = odiparams.getWorkRep();

		// Declare ODI class vars note declaration separate from instantiation so I can use a try and catch on the connection.
		MasterRepositoryDbInfo masterInfo
		WorkRepositoryDbInfo workInfo
		DefaultTransactionDefinition txnDef

		// If odiInstance != null skip (enables operation from ODI Studio?) <DAN NEEDS TESTING>
		if (odiInstance==null) {

			// Connection
			try {
				masterInfo = new MasterRepositoryDbInfo(this.url,this.drv,this.db_usr, this.db_pass.toCharArray(), new PoolingAttributes());
				workInfo = new WorkRepositoryDbInfo(this.work_rep, new PoolingAttributes());
				odiInstance=OdiInstance.createInstance(new OdiInstanceConfig(masterInfo,workInfo));
				auth = odiInstance.getSecurityManager().createAuthentication(this.odi_usr,this.odi_pass.toCharArray());
				odiInstance.getSecurityManager().setCurrentThreadAuthentication(auth);
			}
			catch (Exception e) {
				e.printStackTrace()  // Stack Trace If Required
			}
			finally {
				if (odiInstance==null) {
					println("There Was An Issue Establishing Connection to ODI.")
				}
				else {
					println("Successful Connection to ODI")
					return odiInstance
				}

			} // Finally
		} // If
	} // odiConnect

} // class

// Run Local Test For Class
def runLclConn = new OdiConnection()
runLclConn.odiConnect('src/config/')