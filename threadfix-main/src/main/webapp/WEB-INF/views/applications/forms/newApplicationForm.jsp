<%@ include file="/common/taglibs.jsp"%>

<div class="modal-header">
	<h4 id="myModalLabel">New Application</h4>
</div>
<spring:url value="/organizations/{orgId}/modalAddApp" var="saveUrl">
	<spring:param name="orgId" value="${ organization.id }"/>
</spring:url>
<form:form style="margin-bottom:0px;" id="myAppForm${ organization.id }" modelAttribute="application" method="post" autocomplete="off" action="${fn:escapeXml(saveUrl)}">
	<div class="modal-body">
		<table>
			<tr class="left-align">
				<td style="padding:5px;">Name</td> 
				<td style="padding:5px;">
					<form:input style="margin-bottom:0px;" id="nameInput${organization.id}" path="name" cssClass="focus" size="50" maxlength="60" />
				  	<form:errors path="name" cssClass="errors" />
				</td>
			</tr>
			<tr class="left-align">
				<td style="padding:5px;">URL</td>
				<td style="padding:5px;">
					<form:input style="margin-bottom:0px;" id="urlInput${organization.id}" path="url" size="50" maxlength="255" />
				  	<form:errors path="url" cssClass="errors" />
			  	</td>
			</tr>
			<tr class="left-align">
				<td style="padding:5px;">Unique ID</td>
				<td style="padding:5px;">
					<form:input style="margin-bottom:0px;" id="uniqueIdInput${organization.id}" path="uniqueId" size="50" maxlength="255" />
				  	<form:errors path="uniqueId" cssClass="errors" />
			  	</td>
			</tr>
			<tr class="left-align">
				<td style="padding:5px;">Team</td>
				<td style="padding:5px;"><c:out value="${ organization.name }"/></td>
			</tr>
			<tr class="left-align">
				<td style="padding:5px;">Criticality</td>
				<td style="padding:5px;">
					<form:select style="margin-bottom:0px;" id="criticalityId${organization.id}" path="applicationCriticality.id">
						<form:options items="${applicationCriticalityList}" itemValue="id" itemLabel="name"/>
					</form:select>
					<form:errors path="applicationCriticality.id" cssClass="errors" />
				</td>
			</tr>
			<tr>
				<td class="right-align" style="padding:5px;">Application Type</td>
				<td class="left-align"  style="padding:5px;">
					<form:select path="frameworkType"
                                 id="frameworkTypeSelect${organization.id}"
						items="${ applicationTypes }"
						itemLabel="displayName"/>
				</td>
			</tr>
			<tr>
				<td class="right-align" style="padding:5px;">Source Code URL:</td>
				<td class="left-align"  style="padding:5px;">
					<form:input id="repositoryUrl${organization.id}" maxlength="250" path="repositoryUrl"/>
                    <form:errors path="repositoryUrl" cssClass="errors" />
				</td>
			</tr>			
			<tr>
				<td class="right-align" style="padding:5px;">Source Code Folder:</td>
				<td class="left-align"  style="padding:5px;">
					<form:input id="repositoryFolder${organization.id}" maxlength="250" path="repositoryFolder"/>
					<form:errors path="repositoryFolder" cssClass="errors" />
				</td>
			</tr>
		</table>
	</div>
	<div class="modal-footer">
		<button class="btn" data-dismiss="modal" aria-hidden="true">Close</button>
		<a id="submitAppModal${organization.id}" class="modalSubmit btn btn-primary"
			data-success-div="teamTable" data-success-click="teamCaret${ organization.id }">Add Application</a>
	</div>
</form:form>
