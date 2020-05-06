/*
 * Copyright (c) 2010-2017 Evolveum and contributors
 *
 * This work is dual-licensed under the Apache License 2.0
 * and European Union Public License. See LICENSE file for details.
 */
package com.evolveum.midpoint.model.impl.lens.construction;

import java.io.Serializable;

import com.evolveum.midpoint.model.impl.lens.AssignmentPathImpl;
import com.evolveum.midpoint.model.impl.lens.LensContext;
import com.evolveum.midpoint.prism.OriginType;
import com.evolveum.midpoint.prism.PrismContext;
import com.evolveum.midpoint.prism.delta.DeltaSetTriple;
import com.evolveum.midpoint.prism.delta.PlusMinusZero;
import com.evolveum.midpoint.prism.util.ObjectDeltaObject;
import com.evolveum.midpoint.repo.common.ObjectResolver;
import com.evolveum.midpoint.util.DebugDumpable;
import com.evolveum.midpoint.util.logging.Trace;
import com.evolveum.midpoint.util.logging.TraceManager;
import com.evolveum.midpoint.xml.ns._public.common.common_3.AbstractConstructionType;
import com.evolveum.midpoint.xml.ns._public.common.common_3.AssignmentHolderType;
import com.evolveum.midpoint.xml.ns._public.common.common_3.ConstructionStrengthType;
import com.evolveum.midpoint.xml.ns._public.common.common_3.ObjectType;

/**
 * @author Radovan Semancik
 */
public abstract class AbstractConstruction<AH extends AssignmentHolderType, ACT extends AbstractConstructionType, EC extends EvaluatedConstructible<AH>> implements DebugDumpable, Serializable {

    private static final Trace LOGGER = TraceManager.getTrace(AbstractConstruction.class);

    private AssignmentPathImpl assignmentPath;
    private ACT constructionType;
    private ObjectType source;
    private OriginType originType;
    private String channel;
    private LensContext<AH> lensContext;
    private ObjectDeltaObject<AH> focusOdo;
    private ObjectResolver objectResolver;
    private PrismContext prismContext;
    private boolean isValid = true;
    private boolean wasValid = true;
    private PlusMinusZero relativityMode;

    public AbstractConstruction(ACT constructionType, ObjectType source) {
        this.constructionType = constructionType;
        this.source = source;
        this.assignmentPath = null;
    }

    public void setSource(ObjectType source) {
        this.source = source;
    }

    public ObjectType getSource() {
        return source;
    }

    public OriginType getOriginType() {
        return originType;
    }

    public void setOriginType(OriginType originType) {
        this.originType = originType;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public LensContext<AH> getLensContext() {
        return lensContext;
    }

    public void setLensContext(LensContext<AH> lensContext) {
        this.lensContext = lensContext;
    }

    public ACT getConstructionType() {
        return constructionType;
    }

    public ObjectDeltaObject<AH> getFocusOdo() {
        return focusOdo;
    }

    public void setFocusOdo(ObjectDeltaObject<AH> focusOdo) {
        if (focusOdo.getDefinition() == null) {
            throw new IllegalArgumentException("No definition in focus ODO "+focusOdo);
        }
        this.focusOdo = focusOdo;
    }

    public ObjectResolver getObjectResolver() {
        return objectResolver;
    }

    public void setObjectResolver(ObjectResolver objectResolver) {
        this.objectResolver = objectResolver;
    }

    PrismContext getPrismContext() {
        return prismContext;
    }

    public void setPrismContext(PrismContext prismContext) {
        this.prismContext = prismContext;
    }

    public String getDescription() {
        return constructionType.getDescription();
    }

    public boolean isWeak() {
        return constructionType.getStrength() == ConstructionStrengthType.WEAK;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean isValid) {
        this.isValid = isValid;
    }

    public boolean getWasValid() {
        return wasValid;
    }

    public void setWasValid(boolean wasValid) {
        this.wasValid = wasValid;
    }

    public PlusMinusZero getRelativityMode() {
        return relativityMode;
    }

    public void setRelativityMode(PlusMinusZero relativityMode) {
        this.relativityMode = relativityMode;
    }

    public AssignmentPathImpl getAssignmentPath() {
        return assignmentPath;
    }

    public void setAssignmentPath(AssignmentPathImpl assignmentPath) {
        this.assignmentPath = assignmentPath;
    }

    public abstract DeltaSetTriple<EC> getEvaluatedConstructionTriple();

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((assignmentPath == null) ? 0 : assignmentPath.hashCode());
        result = prime * result + ((channel == null) ? 0 : channel.hashCode());
        result = prime * result + ((constructionType == null) ? 0 : constructionType.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        AbstractConstruction other = (AbstractConstruction) obj;
        if (assignmentPath == null) {
            if (other.assignmentPath != null) {
                return false;
            }
        } else if (!assignmentPath.equals(other.assignmentPath)) {
            return false;
        }
        if (channel == null) {
            if (other.channel != null) {
                return false;
            }
        } else if (!channel.equals(other.channel)) {
            return false;
        }
        if (constructionType == null) {
            if (other.constructionType != null) {
                return false;
            }
        } else if (!constructionType.equals(other.constructionType)) {
            return false;
        }
        if (focusOdo == null) {
            if (other.focusOdo != null) {
                return false;
            }
        } else if (!focusOdo.equals(other.focusOdo)) {
            return false;
        }
        if (isValid != other.isValid) {
            return false;
        }
        if (lensContext == null) {
            if (other.lensContext != null) {
                return false;
            }
        } else if (!lensContext.equals(other.lensContext)) {
            return false;
        }
        if (originType != other.originType) {
            return false;
        }
        if (source == null) {
            if (other.source != null) {
                return false;
            }
        } else if (!source.equals(other.source)) {
            return false;
        }
        return true;
    }

    /**
     * Typical reason for being ignored is that the resourceRef cannot be resolved.
     */
    abstract public boolean isIgnored();
}