/*
 *Project: glorypty-crawler
 *File: edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig.java <2015年7月7日>
 ****************************************************************
 * 版权所有@2015 国裕网络科技  保留所有权利.
 ***************************************************************/
package edu.uci.ics.crawler4j.robotstxt;

/**
*
* @Author hardy 
* @Date 2015年7月7日 上午10:27:43
* @version 1.0
*/
public class RobotstxtConfig {
  /**
   * Should the crawler obey Robots.txt protocol? More info on Robots.txt is
   * available at http://www.robotstxt.org/
   * @Rewrite hardy<2015-07-07> 变革默认值true=>false,即不遵循Robot规则，默认爬取
   */
  private boolean enabled = false; 

  /**
   * user-agent name that will be used to determine whether some servers have
   * specific rules for this agent name.
   */
  private String userAgentName = "crawler4j";

  /**
   * The maximum number of hosts for which their robots.txt is cached.
   */
  private int cacheSize = 500;

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public String getUserAgentName() {
    return userAgentName;
  }

  public void setUserAgentName(String userAgentName) {
    this.userAgentName = userAgentName;
  }

  public int getCacheSize() {
    return cacheSize;
  }

  public void setCacheSize(int cacheSize) {
    this.cacheSize = cacheSize;
  }
}
