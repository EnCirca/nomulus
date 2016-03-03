// Copyright 2016 Google Inc. All Rights Reserved.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.domain.registry.model.eppoutput;

import com.google.common.collect.ImmutableList;
import com.google.domain.registry.model.Buildable;
import com.google.domain.registry.model.ImmutableObject;
import com.google.domain.registry.model.contact.ContactResource;
import com.google.domain.registry.model.domain.DomainApplication;
import com.google.domain.registry.model.domain.DomainRenewData;
import com.google.domain.registry.model.domain.DomainResource;
import com.google.domain.registry.model.domain.fee.FeeCheckResponseExtension;
import com.google.domain.registry.model.domain.fee.FeeCreateResponseExtension;
import com.google.domain.registry.model.domain.fee.FeeDeleteResponseExtension;
import com.google.domain.registry.model.domain.fee.FeeInfoResponseExtension;
import com.google.domain.registry.model.domain.fee.FeeRenewResponseExtension;
import com.google.domain.registry.model.domain.fee.FeeTransferResponseExtension;
import com.google.domain.registry.model.domain.fee.FeeUpdateResponseExtension;
import com.google.domain.registry.model.domain.launch.LaunchCheckResponseExtension;
import com.google.domain.registry.model.domain.launch.LaunchCreateResponseExtension;
import com.google.domain.registry.model.domain.launch.LaunchInfoResponseExtension;
import com.google.domain.registry.model.domain.rgp.RgpInfoExtension;
import com.google.domain.registry.model.domain.secdns.SecDnsInfoExtension;
import com.google.domain.registry.model.eppcommon.Trid;
import com.google.domain.registry.model.eppoutput.CheckData.ContactCheckData;
import com.google.domain.registry.model.eppoutput.CheckData.DomainCheckData;
import com.google.domain.registry.model.eppoutput.CheckData.HostCheckData;
import com.google.domain.registry.model.eppoutput.CreateData.ContactCreateData;
import com.google.domain.registry.model.eppoutput.CreateData.DomainCreateData;
import com.google.domain.registry.model.eppoutput.CreateData.HostCreateData;
import com.google.domain.registry.model.eppoutput.EppOutput.ResponseOrGreeting;
import com.google.domain.registry.model.host.HostResource;
import com.google.domain.registry.model.poll.MessageQueueInfo;
import com.google.domain.registry.model.poll.PendingActionNotificationResponse.ContactPendingActionNotificationResponse;
import com.google.domain.registry.model.poll.PendingActionNotificationResponse.DomainPendingActionNotificationResponse;
import com.google.domain.registry.model.transfer.TransferResponse.ContactTransferResponse;
import com.google.domain.registry.model.transfer.TransferResponse.DomainTransferResponse;

import org.joda.time.DateTime;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 * The Response class represents an EPP response message.
 *
 * <p>From the RFC: "An EPP server responds to a client command by returning a response to the
 * client.  EPP commands are atomic, so a command will either succeed completely or fail completely.
 * Success and failure results MUST NOT be mixed."
 *
 * @see "http://tools.ietf.org/html/rfc5730#section-2.6"
 */
@XmlType(propOrder = {"result", "messageQueueInfo", "resData", "extensions", "trid"})
public class Response extends ImmutableObject implements ResponseOrGreeting {

  /** The TRID associated with this response. */
  @XmlElement(name = "trID")
  Trid trid;

  /** The command result. The RFC allows multiple failure results, but we always return one. */
  Result result;

  /**
   * The time the command that created this response was executed.
   * <p>
   * This is for logging purposes only and is not returned to the user.
   */
  @XmlTransient
  DateTime executionTime;

  /**
   * The repository id of a new object if this is a create response, or null.
   * <p>
   * This is for logging purposes only and is not returned to the user.
   */
  @XmlTransient
  String createdRepoId;

  /**
   * Information about messages queued for retrieval. This may appear in response to any EPP message
   * (if messages are queued), but in practice this will only be set in response to a poll request.
   */
  @XmlElement(name = "msgQ")
  MessageQueueInfo messageQueueInfo;

  /** Zero or more response "resData" results. */
  @XmlElementRefs({
      @XmlElementRef(type = ContactResource.class),
      @XmlElementRef(type = DomainApplication.class),
      @XmlElementRef(type = DomainResource.class),
      @XmlElementRef(type = HostResource.class),
      @XmlElementRef(type = ContactCheckData.class),
      @XmlElementRef(type = ContactCreateData.class),
      @XmlElementRef(type = ContactPendingActionNotificationResponse.class),
      @XmlElementRef(type = ContactTransferResponse.class),
      @XmlElementRef(type = DomainCheckData.class),
      @XmlElementRef(type = DomainCreateData.class),
      @XmlElementRef(type = DomainPendingActionNotificationResponse.class),
      @XmlElementRef(type = DomainRenewData.class),
      @XmlElementRef(type = DomainTransferResponse.class),
      @XmlElementRef(type = HostCheckData.class),
      @XmlElementRef(type = HostCreateData.class)})
  @XmlElementWrapper
  ImmutableList<? extends ResponseData> resData;

  /** Zero or more response extensions. */
  @XmlElementRefs({
      @XmlElementRef(type = FeeCheckResponseExtension.class),
      @XmlElementRef(type = FeeCreateResponseExtension.class),
      @XmlElementRef(type = FeeDeleteResponseExtension.class),
      @XmlElementRef(type = FeeInfoResponseExtension.class),
      @XmlElementRef(type = FeeRenewResponseExtension.class),
      @XmlElementRef(type = FeeTransferResponseExtension.class),
      @XmlElementRef(type = FeeUpdateResponseExtension.class),
      @XmlElementRef(type = LaunchCheckResponseExtension.class),
      @XmlElementRef(type = LaunchCreateResponseExtension.class),
      @XmlElementRef(type = LaunchInfoResponseExtension.class),
      @XmlElementRef(type = RgpInfoExtension.class),
      @XmlElementRef(type = SecDnsInfoExtension.class) })
  @XmlElementWrapper(name = "extension")
  ImmutableList<? extends ResponseExtension> extensions;

  public DateTime getExecutionTime() {
    return executionTime;
  }

  public String getCreatedRepoId() {
    return createdRepoId;
  }

  public ImmutableList<? extends ResponseData> getResponseData() {
    return resData;
  }

  public ImmutableList<? extends ResponseExtension> getExtensions() {
    return extensions;
  }

  public Result getResult() {
    return result;
  }

  /** Marker interface for types that can go in the {@link #resData} field. */
  public interface ResponseData {}

  /** Marker interface for types that can go in the {@link #extensions} field. */
  public interface ResponseExtension {}

  /** Builder for {@link Response} because it is immutable. */
  public static class Builder extends Buildable.Builder<Response> {
    public Builder setTrid(Trid trid) {
      getInstance().trid = trid;
      return this;
    }

    public Builder setResult(Result result) {
      getInstance().result = result;
      return this;
    }

    public Builder setMessageQueueInfo(MessageQueueInfo messageQueueInfo) {
      getInstance().messageQueueInfo = messageQueueInfo;
      return this;
    }

    public Builder setExecutionTime(DateTime executionTime) {
      getInstance().executionTime = executionTime;
      return this;
    }

    public Builder setCreatedRepoId(String createdRepoId) {
      getInstance().createdRepoId = createdRepoId;
      return this;
    }

    public Builder setResData(ImmutableList<? extends ResponseData> resData) {
      getInstance().resData = resData;
      return this;
    }

    public Builder setExtensions(ImmutableList<? extends ResponseExtension> extensions) {
      getInstance().extensions = extensions;
      return this;
    }
  }
}