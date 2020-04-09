/*
 * Copyright (c) 2020 Evolveum and contributors
 *
 * This work is dual-licensed under the Apache License 2.0
 * and European Union Public License. See LICENSE file for details.
 */

package com.evolveum.midpoint.notifications.api.events;

import com.evolveum.midpoint.xml.ns._public.common.common_3.WorkItemNotificationActionType;

/**
 *
 */
public interface WorkItemCustomEvent {
    WorkItemNotificationActionType getNotificationAction();
}
