/*
 * Certain versions of software and/or documents ("Material") accessible here may contain branding from
 * Hewlett-Packard Company (now HP Inc.) and Hewlett Packard Enterprise Company.  As of September 1, 2017,
 * the Material is now offered by Micro Focus, a separately owned and operated company.  Any reference to the HP
 * and Hewlett Packard Enterprise/HPE marks is historical in nature, and the HP and Hewlett Packard Enterprise/HPE
 * marks are the property of their respective owners.
 * __________________________________________________________________
 * MIT License
 *
 * (c) Copyright 2012-2023 Micro Focus or one of its affiliates.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO
 * THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * ___________________________________________________________________
 */

package com.microfocus.application.automation.tools.model;

import hudson.EnvVars;
import hudson.Util;
import hudson.util.Secret;
import hudson.util.VariableResolver;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.kohsuke.stapler.DataBoundConstructor;

public class UploadTestResultToAlmModel {


	public final static int DEFAULT_TIMEOUT = 36000; // 10 hrs
	public final static String ALM_PASSWORD_KEY = "almPassword";
	
	public final static EnumDescription testingFrameworkJunit = new EnumDescription(
			"JUnit", "JUnit");
	public final static EnumDescription testingFrameworkTestNG = new EnumDescription(
			"TestNG", "TestNG");
	public final static EnumDescription testingFrameworkNUnit = new EnumDescription(
			"NUnit", "NUnit");
	public final static List<EnumDescription> testingFrameworks = Arrays.asList(
			testingFrameworkJunit, testingFrameworkTestNG, testingFrameworkNUnit);

	private String almServerName;
	private String almUserName;
	private Secret almPassword;
	private String almDomain;
	private String clientType;
	private String almProject;
	private String almTimeout;
	private String almTestFolder;
	private String almTestSetFolder;
	private String testingFramework;
	private String testingTool;
	private String testingResultFile;
	private String testingAttachments;
	private String jenkinsServerUrl;

	@DataBoundConstructor
	public UploadTestResultToAlmModel(
			String almServerName, String almUserName,
			String almPassword, String almDomain, String clientType, String almProject,
			String testingFramework, String testingTool,
			String almTestFolder , String almTestSetFolder, String almTimeout,
			String testingResultFile, String testingAttachments,
			String jenkinsServerUrl) {

		this.almServerName = almServerName;
		this.almUserName = almUserName;
		this.almPassword = Secret.fromString(almPassword);
		this.almDomain = almDomain;
		this.clientType = clientType;
		this.almProject = almProject;
		
		this.almTimeout=almTimeout;
		this.almTestFolder = almTestFolder;
		this.almTestSetFolder = almTestSetFolder;
		
		this.testingFramework = testingFramework;
		this.testingTool = testingTool;
		
		this.testingResultFile = testingResultFile;
		this.testingAttachments = testingAttachments;
		this.jenkinsServerUrl = jenkinsServerUrl;
	}

	public String getAlmUserName() {
		return almUserName;
	}

	public String getAlmDomain() {
		return almDomain;
	}

	public String getClientType() {
		return clientType;
	}

	public String getAlmPassword() {
		return almPassword.getPlainText();
	}

	public String getAlmProject() {
		return almProject;
	}

	public String getTestingFramework() {
		return testingFramework;
	}
	public String getTestingTool() {
		return testingTool;
	}	

	public String getAlmTimeout() {
		return almTimeout;
	}

	public String getAlmTestFolder() {
		return almTestFolder;
	}

	public String getAlmTestSetFolder() {
		return almTestSetFolder;
	}

	public String getAlmServerName() {
		return almServerName;
	}
	
	public String getTestingResultFile() {
		return testingResultFile;
	}

	public String getTestingAttachments() { return testingAttachments; }
	
	public String getJenkinsServerUrl() {
		return jenkinsServerUrl;
	}

	public Properties getProperties(EnvVars envVars,
			VariableResolver<String> varResolver) {
		return CreateProperties(envVars, varResolver);
	}

	public Properties getProperties() {
		return CreateProperties(null, null);
	}

	private Properties CreateProperties(EnvVars envVars,
			VariableResolver<String> varResolver) {
		Properties props = new Properties();

		if (envVars == null) {
			props.put("almUserName", almUserName);
			props.put(ALM_PASSWORD_KEY, almPassword);
			props.put("almDomain", almDomain);
			props.put("almProject", almProject);
		} else {
			props.put("almUserName",
					Util.replaceMacro(envVars.expand(almUserName), varResolver));
			props.put(ALM_PASSWORD_KEY, almPassword);
			props.put("almDomain",
					Util.replaceMacro(envVars.expand(almDomain), varResolver));
			props.put("almProject",
					Util.replaceMacro(envVars.expand(almProject), varResolver));
		}

		if (!StringUtils.isEmpty(this.testingFramework)) {
			props.put("testingFramework" , testingFramework);

		} else {
			props.put("testingFramework", "");
		}
		
		if (!StringUtils.isEmpty(this.testingTool)) {
			props.put("testingTool" , testingTool);

		} else {
			props.put("testingTool", "");
		}
		
		if (!StringUtils.isEmpty(this.almTestFolder)) {
			props.put("almTestFolder" , almTestFolder);

		} else {
			props.put("almTestFolder", "");
		}

		
		if (!StringUtils.isEmpty(this.almTestSetFolder)) {
			props.put("almTestSetFolder" , almTestSetFolder);

		} else {
			props.put("almTestSetFolder", "");
		}
		
		if (StringUtils.isEmpty(almTimeout)) {
			props.put("almTimeout", "-1");
		} else {
			props.put("almTimeout", almTimeout);
		}

		if (!StringUtils.isEmpty(this.testingResultFile)) {
			props.put("testingResultFile" , testingResultFile);

		} else {
			props.put("testingResultFile", "");
		}

		if (!StringUtils.isEmpty(this.testingAttachments)) {
			props.put("testingAttachments" , testingAttachments);

		} else {
			props.put("testingAttachments", "");
		}

		if (!StringUtils.isEmpty(this.jenkinsServerUrl)) {
			props.put("jenkinsServerUrl" , jenkinsServerUrl);

		} else {
			props.put("jenkinsServerUrl", "");
		}		

		
		return props;
	}
	
	public String toString(){
		  return almServerName + "," +
				  almUserName + "," +
				  almPassword + "," +
				  almDomain + "," +
				  clientType + "," +
				  almProject + "," +
				  almTimeout + "," +
				  almTestFolder + "," +
				  almTestSetFolder + "," +
				  testingFramework + "," +
				  testingTool+ "," +
				  testingResultFile + "," +
				  jenkinsServerUrl;
	}
}
