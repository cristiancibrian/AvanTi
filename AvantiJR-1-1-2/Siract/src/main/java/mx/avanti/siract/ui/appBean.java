package mx.avanti.siract.ui;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import mx.avanti.siract.util.MyUtil;

/**
 *
 * @author Javier
 */
@ManagedBean
@ApplicationScoped
public class appBean {

    public appBean() {
    }

    public String getBaseUrl() {
        return MyUtil.baseurl();
    }
}
