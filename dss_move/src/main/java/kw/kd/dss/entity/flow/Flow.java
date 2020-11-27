/*
 * Copyright 2019 WeBank
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package kw.kd.dss.entity.flow;

import com.webank.wedatasphere.dss.common.entity.flow.FlowVersion;

import java.util.List;

/**
 * Created by enjoyyin on 2019/5/14.
 */
public interface Flow {
    Long getId();

    void setId(Long id);

    String getName();

    void setName(String name);

    String getFlowType();

    void setFlowType(String flowType);

    String getDescription();

    void setDescription(String description);

    Boolean getRootFlow();

    List<? extends com.webank.wedatasphere.dss.common.entity.flow.Flow> getChildren();

    void setChildren(List<? extends com.webank.wedatasphere.dss.common.entity.flow.Flow> children);

    List<? extends FlowVersion> getFlowVersions();

    void setFlowVersions(List<? extends FlowVersion> flowVersions);

    void addFlowVersion(FlowVersion flowVersion);
}
