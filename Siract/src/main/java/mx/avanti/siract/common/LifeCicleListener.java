package mx.avanti.siract.common;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Cesar Favela
 */
public class LifeCicleListener implements PhaseListener {

    @Override
    public void afterPhase(PhaseEvent event) {
        FacesContext facesContext = event.getFacesContext();
        String correntPage = facesContext.getViewRoot().getViewId();
        boolean IsLoginPage = (correntPage.lastIndexOf("public.jsp") > -1);
        HttpSession sesion = (HttpSession) facesContext.getExternalContext().getSession(true);
        Object Usuario = sesion.getAttribute("user");

        if (!IsLoginPage && Usuario == null) {
            NavigationHandler nh = facesContext.getApplication().getNavigationHandler();
            nh.handleNavigation(facesContext, null, "public.jsp");
        }
    }

    @Override
    public void beforePhase(PhaseEvent pe) {
        System.out.println("STAR PHASE" + pe.getPhaseId());
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.ANY_PHASE;
    }
}
