/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ir.moke.jca.app;

import ir.moke.jca.api.Filter;
import ir.moke.jca.api.TailListener;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;

@MessageDriven(activationConfig = {@ActivationConfigProperty(propertyName = "file", propertyValue = "/var/log/syslog")})
public class TailSyslog implements TailListener {

    @Filter(".*mah454:.*")
    public void readMah454SyslogMessages(String message) {
        System.out.println("Mah454 send a message: " + message);
    }

    @Filter(".*root:.*")
    public void readRootSyslogMessages(String message) {
        System.out.println("Root send a message: " + message);
    }

    @Filter(".*custom:.*")
    public void readCustomSyslogMessages(String message) {
        System.out.println("Custom send a message: " + message);
    }

    @Filter("^((?!root|mah454|custom).)*$")
    public void readOtherMessages(String message) {
        System.out.println("Other processor send a message: " + message);
    }
}
