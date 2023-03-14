package fortune.teller.web.portlet;

import fortune.teller.web.constants.FortuneTellerWebPortletKeys;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

import javax.portlet.Portlet;

import org.osgi.service.component.annotations.Component;

/**
 * @author quanleminh
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=training.fortune_teller",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.instanceable=false",
		"javax.portlet.display-name=FortuneTellerWeb",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + FortuneTellerWebPortletKeys.FORTUNE_TELLER_WEB,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class FortuneTellerWebPortlet extends MVCPortlet {
}