chef_dir = File.expand_path(File.dirname(__FILE__))+ "/../"
file_cache_path "#{chef_dir}/cache"
file_backup_path "#{chef_dir}/backup"
cookbook_path ["#{chef_dir}/chef-repo/cookbooks"]
