// -- BEGIN LICENSE BLOCK ----------------------------------------------
// Copyright 2021 FZI Forschungszentrum Informatik
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
// -- END LICENSE BLOCK ------------------------------------------------

//----------------------------------------------------------------------
/*!\file
 *
 * \author  Felix Exner exner@fzi.de
 * \date    2020-09-16
 *
 */
//----------------------------------------------------------------------

package de.fzi.ros_as_a_service.impl;

import com.ur.urcap.api.contribution.ContributionProvider;
import com.ur.urcap.api.contribution.ViewAPIProvider;
import com.ur.urcap.api.contribution.program.swing.SwingProgramNodeView;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ServiceCallerProgramNodeView
    implements SwingProgramNodeView<ServiceCallerProgramNodeContribution> {
  private final ViewAPIProvider apiProvider;

  public ServiceCallerProgramNodeView(ViewAPIProvider apiProvider) {
    this.apiProvider = apiProvider;
  }

  private JComboBox<String> masterComboBox = new JComboBox<String>();
  private JComboBox<String> topicComboBox = new JComboBox<String>();
  private JPanel request_panel = new JPanel();

  @Override
  public void buildUI(
      JPanel panel, ContributionProvider<ServiceCallerProgramNodeContribution> provider) {
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

    panel.add(createDescription("Select the service that you want to call"));
    panel.add(createMasterComboBox(masterComboBox, provider));
    panel.add(createTopicComboBox(topicComboBox, provider));
    panel.add(createVertSeparator(10));
    panel.add(request_panel);
  }

  public void setMasterComboBoxItems(String[] items) {
    masterComboBox.removeAllItems();
    masterComboBox.setModel(new DefaultComboBoxModel<String>(items));
  }

  public void setTopicComboBoxItems(String[] items) {
    topicComboBox.removeAllItems();
    topicComboBox.setModel(new DefaultComboBoxModel<String>(items));
  }

  public void setMasterComboBoxSelection(String item) {
    masterComboBox.setSelectedItem(item);
  }

  public void settopicComboBoxSelection(String item) {
    topicComboBox.setSelectedItem(item);
  }

  private Box createDescription(String desc) {
    Box box = Box.createHorizontalBox();
    box.setAlignmentX(Component.LEFT_ALIGNMENT);

    JLabel label = new JLabel(desc);
    box.add(label);

    return box;
  }

  private Box createMasterComboBox(final JComboBox<String> combo,
      final ContributionProvider<ServiceCallerProgramNodeContribution> provider) {
    Box box = Box.createHorizontalBox();
    box.setAlignmentX(Component.LEFT_ALIGNMENT);
    JLabel label = new JLabel("Remote master");

    combo.setPreferredSize(new Dimension(200, 30));
    combo.setMaximumSize(combo.getPreferredSize());

    combo.addItemListener(new ItemListener() {
      @Override
      public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.DESELECTED) {
          provider.get().onMasterSelection((String) e.getItem());
        }
      }
    });

    box.add(label);
    box.add(combo);

    return box;
  }

  private Box createTopicComboBox(final JComboBox<String> combo,
      final ContributionProvider<ServiceCallerProgramNodeContribution> provider) {
    Box box = Box.createHorizontalBox();
    box.setAlignmentX(Component.LEFT_ALIGNMENT);
    JLabel label = new JLabel("Topic");

    combo.setPreferredSize(new Dimension(200, 30));
    combo.setMaximumSize(combo.getPreferredSize());

    combo.addItemListener(new ItemListener() {
      @Override
      public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.DESELECTED) {
          provider.get().onTopicSelection((String) e.getItem());
        }
      }
    });

    box.add(label);
    box.add(combo);

    return box;
  }

  private Component createHorSpacer(int width) {
    return Box.createRigidArea(new Dimension(width, 0));
  }

  private Component createVertSeparator(int height) {
    return Box.createRigidArea(new Dimension(0, height));
  }
}
