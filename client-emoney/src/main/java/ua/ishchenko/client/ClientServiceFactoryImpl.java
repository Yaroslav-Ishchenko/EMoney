/*******************************************************************************
 * Copyright 2011 Google Inc. All Rights Reserved.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package ua.ishchenko.client;

import org.fusesource.restygwt.client.Defaults;

import ua.ishchenko.client.services.UserService;

import com.google.gwt.core.client.GWT;

/**
 * Sample implementation of {@link ClientFactory}.
 */
public class ClientServiceFactoryImpl implements ClientServiceFactory {
	private String serviceBaseUrl;
	public static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss Z";

	private void ensureRestyIsConfigured() {
		if (serviceBaseUrl == null) {
			serviceBaseUrl ="";// wsConst.wsUrl();
			Defaults.setServiceRoot(serviceBaseUrl);
			Defaults.setDateFormat(DEFAULT_DATETIME_FORMAT);
		}
	}

	@Override
	public UserService getUserService() {
		ensureRestyIsConfigured();
		return GWT.create(UserService.class);
	}
}
