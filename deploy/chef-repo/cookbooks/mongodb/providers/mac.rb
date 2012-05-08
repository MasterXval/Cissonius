#
# Author:: Romain Griffiths (romain.griffiths@gmail.com)
# Cookbook Name:: mongodb
# Provider:: mac
#
# Copyright 2012, Romain Griffiths
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.macports.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

#see https://gist.github.com/661713
action :enable do
  cmd = Chef::ShellOut.new(
                               %Q[ launchctl load -w /Library/LaunchDaemons/org.macports.mongodb.plist 
                                 ] ).run_command
  unless cmd.exitstatus == 0
    Chef::Application.fatal!("Failed to enable mongodb !")
  end
end

action :start do
  cmd = Chef::ShellOut.new( %Q[ launchctl load -w /Library/LaunchDaemons/org.macports.mongodb.plist; ] ).run_command
  unless cmd.exitstatus == 0
    Chef::Application.fatal!("Failed to start mongodb !")
  end
  cmd = Chef::ShellOut.new( %Q[ mongo /tmp/replica_initiate.js ] ).run_command
  unless cmd.exitstatus == 0
    Chef::Application.fatal!("Failed to initialize replica")
  end

end

action :stop do
  cmd = Chef::ShellOut.new(
                               %Q[ launchctl unload -w /Library/LaunchDaemons/org.macports.mongodb.plist 
                                 ] ).run_command
  unless cmd.exitstatus == 0
    Chef::Application.fatal!("Failed to stop mongodb !")
  end
end


