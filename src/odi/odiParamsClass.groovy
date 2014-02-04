// Author: Rittman Mead
//
// Class : odi params
// Change :
// Desc : loads the ODI information from a Connection and a Interface Configuration file
// Note end solution these config files will be produced as output from a users GUI swing dialog.
package odi


class OdiParams {

	ConfigObject intBuildConfigSlurp, connConfigSlurp; // Interface and Connection Config Slurper Objects
	String srcPath;
	String intBuildFileName, connFileName;
	
	// ODI Connection parameters
	String url;
	String driver;
	String master_user;
	String master_pass;
	String work_user;
	String work_pass;
	String workrep;
	String odi_user;
	String odi_pass;
	
	// Interface Auto Build parameters
	String source_model_folder;
	String stage_model_folder;
	String found_model_folder;
	String lkm_name;
	String ikm_name;
	String ckm_name;
	String cdc_option;
	String stage_datastores;
	String found_datastores;
	String build_for_context;
	String parent_proj_folder;
	String sub_proj_folder;
   
	// constructor
	def loadParams(path,iIntFilename,iConnFileName){
	  
	  // Create a new ConfigSlurper and Load RM ODI Build Generator File
	  srcPath = path
	  connFileName = iConnFileName
	  intBuildFileName = iIntFilename
	  
	  connConfigSlurp = new ConfigSlurper().parse(new File(srcPath+connFileName).toURL())
	  intBuildConfigSlurp = new ConfigSlurper().parse(new File(srcPath+intBuildFileName).toURL())
	  
	  // Set Connection Details from Connection Slurp
	  url = connConfigSlurp.url
	  driver = connConfigSlurp.driver
	  master_user = connConfigSlurp.master_user
	  master_pass = connConfigSlurp.master_pass
	  work_user = connConfigSlurp.work_user
	  work_pass = connConfigSlurp.work_pass
	  workrep = connConfigSlurp.workrep
	  odi_user = connConfigSlurp.odi_user
	  odi_pass = connConfigSlurp.odi_pass
	  
	  // Set Connection Details from Interface Build Slurp
	  source_model_folder = intBuildConfigSlurp.intBuildConfigSlurp
	  stage_model_folder = intBuildConfigSlurp.stage_model_folder
	  found_model_folder = intBuildConfigSlurp.found_model_folder
	  lkm_name = intBuildConfigSlurp.lkm_name
	  ikm_name = intBuildConfigSlurp.ikm_name
	  ckm_name = intBuildConfigSlurp.ckm_name
	  cdc_option = intBuildConfigSlurp.cdc_option
	  stage_datastores = intBuildConfigSlurp.stage_datastores
	  found_datastores = intBuildConfigSlurp.found_datastores
	  build_for_context = intBuildConfigSlurp.build_for_context
	  parent_proj_folder = intBuildConfigSlurp.parent_proj_folder
	  sub_proj_folder = intBuildConfigSlurp.sub_proj_folder
	  
	}

	// Getters for JAVA eyes
	public String getUrl() {
		return url;
	}

	public String getDriver() {
		return driver;
	}

	public String getMasterUser() {
		return master_user;
	}

	public String getMasterPass() {
		return master_pass;
	}

	public String getWorkUser() {
		return work_user;
	}

	public String getWorkPass() {
		return work_pass;
	}

	public String getWorkRep() {
		return workrep;
	}

	public String getOdiUser() {
		return odi_user;
	}

	public String getOdiPass() {
		return odi_pass;
	}

	public String getSourceModelFolder() {
		return source_model_folder;
	}

	public String getStageModelFolder() {
		return stage_model_folder;
	}

	public String getFoundModelFolder() {
		return found_model_folder;
	}

	public String getLkmName() {
		return lkm_name;
	}

	public String getIkmName() {
		return ikm_name;
	}

	public String getCkmName() {
		return ckm_name;
	}

	public String getCdcOption() {
		return cdc_option;
	}

	public String getStageDatastores() {
		return stage_datastores;
	}

	public String getFoundDatastores() {
		return found_datastores;
	}

	public String getBuildForContext() {
		return build_for_context;
	}

	public String getParentProjFolder() {
		return parent_proj_folder;
	}

	public String getSubProjFolder() {
		return sub_proj_folder;
	}

}
