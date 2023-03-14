<%@ include file="./init.jsp"%>

<portlet:actionURL var="fortuneTellerActionUrl"
	name="<%=MVCCommandNames.GET_FORTUNE_TELLER_RESULT%>">
	<portlet:param name="redirect" value="${param.redirect}" />
</portlet:actionURL>


<div class="container-fluid-1280 edit-assignment">
	<h1>
		<liferay-ui:message key="fortune-teller" />
	</h1>

	<aui:form action="${fortuneTellerActionUrl}" name="fm">

		<aui:field-wrapper>
			<aui:fieldset>

				<%-- horoscope field. --%>
				<aui:select name="horoscope" id="horoscopeInput"
					label="select-horoscope">
					<c:forEach
						items="<%=FortuneTellerWebPortletKeys.HOROSCOPE_OPTIONS%>"
						var="option">
						<aui:option value="${option}">
							${option}
						</aui:option>
					</c:forEach>
				</aui:select>

				<%-- horoscope field. --%>
				<aui:select name="day" id="dayInput" label="select-day">
					<c:forEach items="<%=FortuneTellerWebPortletKeys.DAY_OPTIONS%>"
						var="dayOption">
						<aui:option value="${dayOption}">
							${dayOption}
						</aui:option>
					</c:forEach>
				</aui:select>
			</aui:fieldset>
		</aui:field-wrapper>

		<%--Buttons. --%>
		<aui:button-row>
			<aui:button cssClass="btn btn-primary" type="submit"
				label="get-fortune-teller" />
		</aui:button-row>
	</aui:form>

	<c:choose>
		<c:when test="${fortuneTellerResult != null}">
			<liferay-ui:message key="your-horoscope-summary" arguments="${chosenHoroscope}"/>
			<ul>
				<li><b>Date range:</b>&nbsp;${fortuneTellerResult.date_range}</li>
				<li><b>Current date:</b>&nbsp;${fortuneTellerResult.current_date}</li>
				<li><b>Description:</b>&nbsp;${fortuneTellerResult.description}</li>
				<li><b>Compatibility:</b>&nbsp;${fortuneTellerResult.compatibility}</li>
				<li><b>Mood:</b>&nbsp;${fortuneTellerResult.mood}</li>
				<li><b>Color:</b>&nbsp;${fortuneTellerResult.color}</li>
				<li><b>Lucky number:</b>&nbsp;${fortuneTellerResult.lucky_number}</li>
				<li><b>Lucky time:</b>&nbsp;${fortuneTellerResult.lucky_time}</li>
			</ul>
		</c:when>
		<c:otherwise>
			<liferay-ui:message key="get-fortune-teller-result-introduction"/>
		</c:otherwise>
	</c:choose>
</div>