package mx.avanti.siract.common;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import mx.avanti.siract.entity.Areaadministrativa;
import mx.avanti.siract.entity.Profesor;
import mx.avanti.siract.helper.FiltrosBeanHelper;

public class ResultadoReporteAdapter extends ArrayList<ResultadoReporte> {

    FiltrosBeanHelper filtrosBean;

    public List<ResultadoReporte> whereProgramaEducativoEquals(String peNombre) {
        ArrayList<ResultadoReporte> aux = new ArrayList<>();
        for (ResultadoReporte rr : this) {
            if (rr.getProgramaEducativo().equalsIgnoreCase(peNombre)) {
                aux.add(rr);
            }
        }

        return aux;
    }

    public ResultadoReporteAdapter whereAreaConocimientoEquals(List<String> acoClave) {
        ResultadoReporteAdapter rra = new ResultadoReporteAdapter();

        for (ResultadoReporte rr : this) {
            for (String clave : acoClave) {
                if (rr.getClaveAreaConocimiento() == Integer.parseInt(clave)) {
                    rra.add(rr);
                }
            }

        }
        return rra;
    }

    public List<ResultadoReporte> whereUnidadAprendizajeEquals(String unidadClave) {
        ArrayList<ResultadoReporte> aux = new ArrayList<>();
        for (ResultadoReporte rr : this) {
            if (rr.getClaveUnidadAprendizaje() == Integer.parseInt(unidadClave)) {
                aux.add(rr);
            }
        }
        return aux;
    }

    public ResultadoReporteAdapter whereProfesorEquals(List<String> numEmpleado, List<String> selectUnidadAprendizaje, List<String> selectGrupo) {
        ResultadoReporteAdapter rra = new ResultadoReporteAdapter();

        for (ResultadoReporte rr : this) {
            for (String numero : numEmpleado) {
                if (rr.getNumEmpleadoProfesor() == Integer.parseInt(numero)) {
                    rra.add(rr);
                }
            }

        }
        return rra.whereUnidadAprendizajeEquals(selectUnidadAprendizaje, selectGrupo);
    }

    public ResultadoReporteAdapter() {
        super();
    }

    public ResultadoReporteAdapter(List<Object[]> resultSet) {
        super();
        ResultadoReporteAdapter rra = new ResultadoReporteAdapter();
        filtrosBean = new FiltrosBeanHelper();

        for (Object[] o : resultSet) {
            int uaipId = (Integer) o[16];
            int pos = findByUaipId(uaipId);
            float avanceRACT = (o[7] != null) ? (Float) o[7] : 0.0f;
            int numeroRACT = (o[9] != null) ? Integer.parseInt(o[9].toString()) : 0;
            String fecha = (o[8] != null) ? o[8].toString() : "0001-01-01";

            if (pos == -1) {
                ResultadoReporte rr = new ResultadoReporte();
                rr.setUaipId(uaipId);
                rr.setIdRACT((Integer) o[0]);
                rr.setClaveUnidadAprendizaje((Integer) o[1]);
                rr.setNumEmpleadoProfesor((Integer) o[2]);
                rr.setNombreProfesor(cambioLetra(o[3].toString()));
                String grupo = o[4].toString() + "-" + o[6].toString() + "-" + o[5].toString();
                rr.setGrupo(grupo);
                rr.setUnidadAprendizaje((o[10] != null) ? o[10].toString() : "?");
                int acoClave = (o[11] != null) ? (Integer) o[11] : (o[20] != null) ? (Integer) o[20] : 0;
                String acoNombre = (o[12] != null) ? o[12].toString() : (o[21] != null) ? o[21].toString() : "";
                rr.setClaveAreaConocimiento(acoClave);
                rr.setAreaConocimiento(cambioLetra(acoNombre));
                rr.setProgramaEducativo(cambioLetra(o[14].toString()));
                rr.setClaveProgramaEducativo(o[13] != null ? Integer.parseInt(o[13].toString()) : 0);
                rr.setNompreResponsablePE(cambioLetra(o[15].toString()));
                rr.setPlanEstudio(o[18].toString());
                Areaadministrativa areaAdmin = new Areaadministrativa();
                areaAdmin = filtrosBean.findUnidadAprendizaje((Integer) o[17]);
                rr.setAreaAdminId(areaAdmin.getAADid() != null ? areaAdmin.getAADid() : 0);
                rr.setAreaAdminNombre(areaAdmin.getAADnombre() != null ? areaAdmin.getAADnombre() : " ");
                Profesor respoAreaAdmin = filtrosBean.getProfesorResponsableAreaAdminByUA(rr.getClaveUnidadAprendizaje(), rr.getClaveProgramaEducativo());
                rr.setUnidadAprendizajeId((Integer) o[17]);
                rr.setProgramaId((Integer) o[19]);
                rr.setProfesorId((Integer) o[20]);
                if (respoAreaAdmin.getPROnombre() != null) {
                    rr.setAreaAdminResp(cambioLetra(respoAreaAdmin.getPROnombre() + " " + respoAreaAdmin.getPROapellidoPaterno() + " " + respoAreaAdmin.getPROapellidoMaterno()));
                } else {
                    rr.setAreaAdminResp("Sin asignar");
                }

                switch (numeroRACT) {
                    case 1:
                        rr.setAvanceRACT1(avanceRACT);
                        rr.setFechaRACT1(Date.valueOf(fecha));
                        break;
                    case 2:
                        rr.setAvanceRACT2(avanceRACT);
                        rr.setFechaRACT2(Date.valueOf(fecha));
                        break;
                    case 3:
                        rr.setAvanceRACT3(avanceRACT);
                        rr.setFechaRACT3(Date.valueOf(fecha));
                        break;

                }
                this.add(rr);
            } else {
                ResultadoReporte rr = this.get(pos);
                switch (numeroRACT) {
                    case 1:
                        rr.setAvanceRACT1(avanceRACT);
                        rr.setFechaRACT1(Date.valueOf(fecha));
                        break;
                    case 2:
                        rr.setAvanceRACT2(avanceRACT);
                        rr.setFechaRACT2(Date.valueOf(fecha));
                        break;
                    case 3:
                        rr.setAvanceRACT3(avanceRACT);
                        rr.setFechaRACT3(Date.valueOf(fecha));
                        break;

                }
                this.remove(pos);
                this.add(pos, rr);
            }

        }

    }

    private int findByUaipId(int uaipId) {
        for (int i = 0; i < size(); i++) {
            if (this.get(i).getUaipId() == uaipId) {
                return i;
            }
        }
        return -1;
    }

    public ResultadoReporteAdapter whereUnidadAprendizajeEquals(List<String> selectUnidadAprendisajeUniForm, List<String> selectGrupoUniForm) {
        ResultadoReporteAdapter aux = new ResultadoReporteAdapter();
        for (ResultadoReporte rr : this) {
            for (String claveUnidad : selectUnidadAprendisajeUniForm) {
                for (String numGrupo : selectGrupoUniForm) {
                    if (rr.getClaveUnidadAprendizaje() == Integer.parseInt(claveUnidad) && rr.getGrupo().contains(numGrupo)) {
                        aux.add(rr);
                    }
                }

            }
        }
        return aux;
    }

    public ResultadoReporteAdapter whereAreaAdminsEquals(List<String> areasAdmin) {
        ResultadoReporteAdapter aux = new ResultadoReporteAdapter();
        int unidadID;

        for (ResultadoReporte rr : this) {
            for (String areaAdmin : areasAdmin) {
                if (rr.getAreaAdminId() == Integer.parseInt(areaAdmin)) {
                    aux.add(rr);
                }
            }

        }
        return aux;
    }

    private String cambioLetra(String nombre) {
        if (nombre.contains("\\")) {
            nombre = nombre.replace("\\", "ñ");
        }
        if (nombre.contains("/")) {
            nombre = nombre.replace("/", "ñ");
        }
        return nombre;
    }
}
