package cn.jedisoft.framework.core.conf;

import java.util.List;

/**
 * AppContext 的参数配置
 * 
 * @author lzm
 *
 */
public class AppContextConf {

	private AutowireDefinition jediAutowire = new AutowireDefinition();
	private List<BeanDefinition> jediBean = null;
	private List<ApiDefinition> jediApi = null;
	private List<String> jediScan = null;
	private List<DsDefinition> jediDs = null;

	public AutowireDefinition getJediAutowire() {
		return jediAutowire;
	}

	public void setJediAutowire(AutowireDefinition jediAutowire) {
		this.jediAutowire = jediAutowire;
	}

	public List<BeanDefinition> getJediBean() {
		return jediBean;
	}

	public void setJediBean(List<BeanDefinition> jediBean) {
		this.jediBean = jediBean;
	}

	public List<ApiDefinition> getJediApi() {
		return jediApi;
	}

	public void setJediApi(List<ApiDefinition> jediApi) {
		this.jediApi = jediApi;
	}

	public List<String> getJediScan() {
		return jediScan;
	}

	public void setJediScan(List<String> jediScan) {
		this.jediScan = jediScan;
	}

	public List<DsDefinition> getJediDs() {
		return jediDs;
	}

	public void setJediDs(List<DsDefinition> jediDs) {
		this.jediDs = jediDs;
	}

}
