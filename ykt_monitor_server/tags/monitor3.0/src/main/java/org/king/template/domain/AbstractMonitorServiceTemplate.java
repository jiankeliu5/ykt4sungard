package org.king.template.domain;

import org.king.support.domain.MonitorCommands;
import org.king.support.domain.MonitorContactgroups;

/**
 * AbstractMonitorServiceTemplate entity provides the base persistence
 * definition of the MonitorServiceTemplate entity. @author MyEclipse
 * Persistence Tools
 */

public abstract class AbstractMonitorServiceTemplate extends
        org.king.framework.domain.BaseObject implements java.io.Serializable {

    // Fields

    private Integer templateId;
    private String templateName;
    private MonitorCommands checkCommand = new MonitorCommands();
    private String checkCommandArgs;
    private MonitorTimePeriods notificationTimeperiod = new MonitorTimePeriods();
    private MonitorTimePeriods checkTimeperiod = new MonitorTimePeriods();
    private Double checkInterval;
    private Double retryInterval;
    private Integer maxCheckAttempts;
    private Double notificationInterval;
    private Integer notifyOnWarning;
    private Integer notifyOnUnknown;
    private Integer notifyOnCritical;
    private Integer notifyOnRecovery;
    private Integer notifyOnFlapping;
    private Integer notifyOnDowntime;
    private Integer flapDetectionEnabled;
    private Integer flapDetectionOnOk;
    private Integer flapDetectionOnWarning;
    private Integer flapDetectionOnUnknown;
    private Integer flapDetectionOnCritical;
    private Double lowFlapThreshold = Double.valueOf(0);;
    private Double highFlapThreshold = Double.valueOf(0);;
    private Integer freshnessChecksEnabled;
    private Integer freshnessThreshold;
    private Integer passiveChecksEnabled;
    private Integer eventHandlerEnabled;
    private Integer activeChecksEnabled;
    private Integer retainStatusInformation;
    private Integer retainNonstatusInformation;
    private Integer notificationsEnabled;
    private String notificationOptions;
    private MonitorContactgroups contactGroups = new MonitorContactgroups();

    // Constructors

    /**
     * default constructor
     */
    public AbstractMonitorServiceTemplate() {
    }

    protected AbstractMonitorServiceTemplate(Integer templateId, String templateName, MonitorCommands checkCommand, String checkCommandArgs, MonitorTimePeriods notificationTimeperiod, MonitorTimePeriods checkTimeperiod, Double checkInterval, Double retryInterval, Integer maxCheckAttempts, Double notificationInterval, Integer notifyOnWarning, Integer notifyOnUnknown, Integer notifyOnCritical, Integer notifyOnRecovery, Integer notifyOnFlapping, Integer notifyOnDowntime, Integer flapDetectionEnabled, Integer flapDetectionOnOk, Integer flapDetectionOnWarning, Integer flapDetectionOnUnknown, Integer flapDetectionOnCritical, Double lowFlapThreshold, Double highFlapThreshold, Integer freshnessChecksEnabled, Integer freshnessThreshold, Integer passiveChecksEnabled, Integer eventHandlerEnabled, Integer activeChecksEnabled, Integer retainStatusInformation, Integer retainNonstatusInformation, Integer notificationsEnabled, String notificationOptions, MonitorContactgroups contactGroups) {
        this.templateId = templateId;
        this.templateName = templateName;
        this.checkCommand = checkCommand;
        this.checkCommandArgs = checkCommandArgs;
        this.notificationTimeperiod = notificationTimeperiod;
        this.checkTimeperiod = checkTimeperiod;
        this.checkInterval = checkInterval;
        this.retryInterval = retryInterval;
        this.maxCheckAttempts = maxCheckAttempts;
        this.notificationInterval = notificationInterval;
        this.notifyOnWarning = notifyOnWarning;
        this.notifyOnUnknown = notifyOnUnknown;
        this.notifyOnCritical = notifyOnCritical;
        this.notifyOnRecovery = notifyOnRecovery;
        this.notifyOnFlapping = notifyOnFlapping;
        this.notifyOnDowntime = notifyOnDowntime;
        this.flapDetectionEnabled = flapDetectionEnabled;
        this.flapDetectionOnOk = flapDetectionOnOk;
        this.flapDetectionOnWarning = flapDetectionOnWarning;
        this.flapDetectionOnUnknown = flapDetectionOnUnknown;
        this.flapDetectionOnCritical = flapDetectionOnCritical;
        this.lowFlapThreshold = lowFlapThreshold;
        this.highFlapThreshold = highFlapThreshold;
        this.freshnessChecksEnabled = freshnessChecksEnabled;
        this.freshnessThreshold = freshnessThreshold;
        this.passiveChecksEnabled = passiveChecksEnabled;
        this.eventHandlerEnabled = eventHandlerEnabled;
        this.activeChecksEnabled = activeChecksEnabled;
        this.retainStatusInformation = retainStatusInformation;
        this.retainNonstatusInformation = retainNonstatusInformation;
        this.notificationsEnabled = notificationsEnabled;
        this.notificationOptions = notificationOptions;
        this.contactGroups = contactGroups;
    }

    /**
     * full constructor
     */


    // Property accessors
    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public MonitorCommands getCheckCommand() {
        return checkCommand;
    }

    public void setCheckCommand(MonitorCommands checkCommand) {
        this.checkCommand = checkCommand;
    }

    public String getCheckCommandArgs() {
        return checkCommandArgs;
    }

    public void setCheckCommandArgs(String checkCommandArgs) {
        this.checkCommandArgs = checkCommandArgs;
    }

    public MonitorTimePeriods getNotificationTimeperiod() {
        return notificationTimeperiod;
    }

    public void setNotificationTimeperiod(MonitorTimePeriods notificationTimeperiod) {
        this.notificationTimeperiod = notificationTimeperiod;
    }

    public MonitorTimePeriods getCheckTimeperiod() {
        return checkTimeperiod;
    }

    public void setCheckTimeperiod(MonitorTimePeriods checkTimeperiod) {
        this.checkTimeperiod = checkTimeperiod;
    }

    public Double getCheckInterval() {
        return checkInterval;
    }

    public void setCheckInterval(Double checkInterval) {
        this.checkInterval = checkInterval;
    }

    public Double getRetryInterval() {
        return retryInterval;
    }

    public void setRetryInterval(Double retryInterval) {
        this.retryInterval = retryInterval;
    }

    public Integer getMaxCheckAttempts() {
        return maxCheckAttempts;
    }

    public void setMaxCheckAttempts(Integer maxCheckAttempts) {
        this.maxCheckAttempts = maxCheckAttempts;
    }

    public Double getNotificationInterval() {
        return notificationInterval;
    }

    public void setNotificationInterval(Double notificationInterval) {
        this.notificationInterval = notificationInterval;
    }

    public Integer getNotifyOnWarning() {
        return notifyOnWarning;
    }

    public void setNotifyOnWarning(Integer notifyOnWarning) {
        this.notifyOnWarning = notifyOnWarning;
    }

    public Integer getNotifyOnUnknown() {
        return notifyOnUnknown;
    }

    public void setNotifyOnUnknown(Integer notifyOnUnknown) {
        this.notifyOnUnknown = notifyOnUnknown;
    }

    public Integer getNotifyOnCritical() {
        return notifyOnCritical;
    }

    public void setNotifyOnCritical(Integer notifyOnCritical) {
        this.notifyOnCritical = notifyOnCritical;
    }

    public Integer getNotifyOnRecovery() {
        return notifyOnRecovery;
    }

    public void setNotifyOnRecovery(Integer notifyOnRecovery) {
        this.notifyOnRecovery = notifyOnRecovery;
    }

    public Integer getNotifyOnFlapping() {
        return notifyOnFlapping;
    }

    public void setNotifyOnFlapping(Integer notifyOnFlapping) {
        this.notifyOnFlapping = notifyOnFlapping;
    }

    public Integer getNotifyOnDowntime() {
        return notifyOnDowntime;
    }

    public void setNotifyOnDowntime(Integer notifyOnDowntime) {
        this.notifyOnDowntime = notifyOnDowntime;
    }

    public Integer getFlapDetectionEnabled() {
        return flapDetectionEnabled;
    }

    public void setFlapDetectionEnabled(Integer flapDetectionEnabled) {
        this.flapDetectionEnabled = flapDetectionEnabled;
    }

    public Integer getFlapDetectionOnOk() {
        return flapDetectionOnOk;
    }

    public void setFlapDetectionOnOk(Integer flapDetectionOnOk) {
        this.flapDetectionOnOk = flapDetectionOnOk;
    }

    public Integer getFlapDetectionOnWarning() {
        return flapDetectionOnWarning;
    }

    public void setFlapDetectionOnWarning(Integer flapDetectionOnWarning) {
        this.flapDetectionOnWarning = flapDetectionOnWarning;
    }

    public Integer getFlapDetectionOnUnknown() {
        return flapDetectionOnUnknown;
    }

    public void setFlapDetectionOnUnknown(Integer flapDetectionOnUnknown) {
        this.flapDetectionOnUnknown = flapDetectionOnUnknown;
    }

    public Integer getFlapDetectionOnCritical() {
        return flapDetectionOnCritical;
    }

    public void setFlapDetectionOnCritical(Integer flapDetectionOnCritical) {
        this.flapDetectionOnCritical = flapDetectionOnCritical;
    }

    public Double getLowFlapThreshold() {
        return lowFlapThreshold;
    }

    public void setLowFlapThreshold(Double lowFlapThreshold) {
        this.lowFlapThreshold = lowFlapThreshold;
    }

    public Double getHighFlapThreshold() {
        return highFlapThreshold;
    }

    public void setHighFlapThreshold(Double highFlapThreshold) {
        this.highFlapThreshold = highFlapThreshold;
    }

    public Integer getFreshnessChecksEnabled() {
        return freshnessChecksEnabled;
    }

    public void setFreshnessChecksEnabled(Integer freshnessChecksEnabled) {
        this.freshnessChecksEnabled = freshnessChecksEnabled;
    }

    public Integer getFreshnessThreshold() {
        return freshnessThreshold;
    }

    public void setFreshnessThreshold(Integer freshnessThreshold) {
        this.freshnessThreshold = freshnessThreshold;
    }

    public Integer getPassiveChecksEnabled() {
        return passiveChecksEnabled;
    }

    public void setPassiveChecksEnabled(Integer passiveChecksEnabled) {
        this.passiveChecksEnabled = passiveChecksEnabled;
    }

    public Integer getEventHandlerEnabled() {
        return eventHandlerEnabled;
    }

    public void setEventHandlerEnabled(Integer eventHandlerEnabled) {
        this.eventHandlerEnabled = eventHandlerEnabled;
    }

    public Integer getActiveChecksEnabled() {
        return activeChecksEnabled;
    }

    public void setActiveChecksEnabled(Integer activeChecksEnabled) {
        this.activeChecksEnabled = activeChecksEnabled;
    }

    public Integer getRetainStatusInformation() {
        return retainStatusInformation;
    }

    public void setRetainStatusInformation(Integer retainStatusInformation) {
        this.retainStatusInformation = retainStatusInformation;
    }

    public Integer getRetainNonstatusInformation() {
        return retainNonstatusInformation;
    }

    public void setRetainNonstatusInformation(Integer retainNonstatusInformation) {
        this.retainNonstatusInformation = retainNonstatusInformation;
    }

    public Integer getNotificationsEnabled() {
        return notificationsEnabled;
    }

    public void setNotificationsEnabled(Integer notificationsEnabled) {
        this.notificationsEnabled = notificationsEnabled;
    }

    public String getNotificationOptions() {
        return notificationOptions;
    }

    public void setNotificationOptions(String notificationOptions) {
        this.notificationOptions = notificationOptions;
    }

    public MonitorContactgroups getContactGroups() {
        return contactGroups;
    }

    public void setContactGroups(MonitorContactgroups contactGroups) {
        this.contactGroups = contactGroups;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 0;
        result = prime * result
                + ((checkCommand == null) ? 0 : checkCommand.hashCode());
        result = prime * result
                + ((checkInterval == null) ? 0 : checkInterval.hashCode());
        result = prime * result
                + ((checkTimeperiod == null) ? 0 : checkTimeperiod.hashCode());
        result = prime * result
                + ((contactGroups == null) ? 0 : contactGroups.hashCode());
        result = prime
                * result
                + ((eventHandlerEnabled == null) ? 0 : eventHandlerEnabled
                .hashCode());
        result = prime
                * result
                + ((flapDetectionEnabled == null) ? 0 : flapDetectionEnabled
                .hashCode());
        result = prime
                * result
                + ((maxCheckAttempts == null) ? 0 : maxCheckAttempts.hashCode());
        result = prime
                * result
                + ((notificationInterval == null) ? 0 : notificationInterval
                .hashCode());
        result = prime
                * result
                + ((notificationOptions == null) ? 0 : notificationOptions
                .hashCode());
        result = prime
                * result
                + ((notificationTimeperiod == null) ? 0 : notificationTimeperiod
                .hashCode());
        result = prime
                * result
                + ((notificationsEnabled == null) ? 0 : notificationsEnabled
                .hashCode());
        result = prime
                * result
                + ((retainNonstatusInformation == null) ? 0
                : retainNonstatusInformation.hashCode());
        result = prime
                * result
                + ((retainStatusInformation == null) ? 0
                : retainStatusInformation.hashCode());
        result = prime * result
                + ((retryInterval == null) ? 0 : retryInterval.hashCode());
        result = prime * result
                + ((templateId == null) ? 0 : templateId.hashCode());
        result = prime * result
                + ((templateName == null) ? 0 : templateName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof AbstractMonitorServiceTemplate))
            return false;
        AbstractMonitorServiceTemplate other = (AbstractMonitorServiceTemplate) obj;
        if (checkCommand == null) {
            if (other.checkCommand != null)
                return false;
        } else if (!checkCommand.equals(other.checkCommand))
            return false;
        if (checkInterval == null) {
            if (other.checkInterval != null)
                return false;
        } else if (!checkInterval.equals(other.checkInterval))
            return false;
        if (checkTimeperiod == null) {
            if (other.checkTimeperiod != null)
                return false;
        } else if (!checkTimeperiod.equals(other.checkTimeperiod))
            return false;
        if (contactGroups == null) {
            if (other.contactGroups != null)
                return false;
        } else if (!contactGroups.equals(other.contactGroups))
            return false;
        if (eventHandlerEnabled == null) {
            if (other.eventHandlerEnabled != null)
                return false;
        } else if (!eventHandlerEnabled.equals(other.eventHandlerEnabled))
            return false;
        if (flapDetectionEnabled == null) {
            if (other.flapDetectionEnabled != null)
                return false;
        } else if (!flapDetectionEnabled.equals(other.flapDetectionEnabled))
            return false;
        if (maxCheckAttempts == null) {
            if (other.maxCheckAttempts != null)
                return false;
        } else if (!maxCheckAttempts.equals(other.maxCheckAttempts))
            return false;
        if (notificationInterval == null) {
            if (other.notificationInterval != null)
                return false;
        } else if (!notificationInterval.equals(other.notificationInterval))
            return false;
        if (notificationOptions == null) {
            if (other.notificationOptions != null)
                return false;
        } else if (!notificationOptions.equals(other.notificationOptions))
            return false;
        if (notificationTimeperiod == null) {
            if (other.notificationTimeperiod != null)
                return false;
        } else if (!notificationTimeperiod.equals(other.notificationTimeperiod))
            return false;
        if (notificationsEnabled == null) {
            if (other.notificationsEnabled != null)
                return false;
        } else if (!notificationsEnabled.equals(other.notificationsEnabled))
            return false;
        if (retainNonstatusInformation == null) {
            if (other.retainNonstatusInformation != null)
                return false;
        } else if (!retainNonstatusInformation
                .equals(other.retainNonstatusInformation))
            return false;
        if (retainStatusInformation == null) {
            if (other.retainStatusInformation != null)
                return false;
        } else if (!retainStatusInformation
                .equals(other.retainStatusInformation))
            return false;
        if (retryInterval == null) {
            if (other.retryInterval != null)
                return false;
        } else if (!retryInterval.equals(other.retryInterval))
            return false;
        if (templateId == null) {
            if (other.templateId != null)
                return false;
        } else if (!templateId.equals(other.templateId))
            return false;
        if (templateName == null) {
            if (other.templateName != null)
                return false;
        } else if (!templateName.equals(other.templateName))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "AbstractMonitorServiceTemplate [checkCommand=" + checkCommand
                + ", checkInterval=" + checkInterval + ", checkTimeperiod="
                + checkTimeperiod + ", contactGroups=" + contactGroups
                + ", eventHandlerEnabled=" + eventHandlerEnabled
                + ", flapDetectionEnabled=" + ", maxCheckAttempts="
                + maxCheckAttempts + ", notificationInterval="
                + notificationInterval + ", notificationOptions="
                + notificationOptions + ", notificationTimeperiod="
                + notificationTimeperiod + ", notificationsEnabled="
                + notificationsEnabled + ", retainNonstatusInformation="
                + retainNonstatusInformation + ", retainStatusInformation="
                + retainStatusInformation + ", retryInterval=" + retryInterval
                + ", templateId=" + templateId + ", templateName="
                + templateName + "]";
	}

}