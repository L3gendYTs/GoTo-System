![Img](https://i.imgur.com/pmrFShw.png)

# GoTo-System

This is a simple plugin created for a network, which I then decided to release to everyone. What this plugin does is add the /goto command that allows you to go from one server to another without having to do /server.


## Command:
- /goto \<server> o /connect \<server>
- /gotoreload

## Permissions:
- goto.command | To use the command
- goto.reload | To reload the plugin!
- goto.update | To receive plugin update notifications!
- goto.server.\<servername> | To use /goto <servername> or goto.all for all servers
 


## Config example/backup
```yaml
#           ______      __
#          / ____/___  / /_____
#         / / __/ __ \/ __/ __ \
#        / /_/ / /_/ / /_/ /_/ /
#   _____\____/\____/\__/\____/
#  / ___/__  _______/ /____  ____ ___
#  \__ \/ / / / ___/ __/ _ \/ __ `__ \
# ___/ / /_/ (__  ) /_/  __/ / / / / /
#/____/\__, /____/\__/\___/_/ /_/ /_/
#     /____/
# GoTo System | Version 2.0

no-perms: "&cYou don't have the permission to perform this command!"
no-console: "&cThis command can only be executed by a player!"
usage: "&cUsage: /goto <server>"
no-found: "&cThis server does not exist!"
reload: "&9&lGoToSystem &7// &aPlugin reloaded successfully!"

# When true, players without `goto.all` must have `goto.server.<server>`
# to connect to a specific server.
per-server-permission: true

update-check:
  enabled: true
  message: "&9&lGoToSystem &7// A new update available. {OLD_VERSION} -> {NEW_VERSION}"
```

You are strictly prohibited from claiming this plugin or its code as your own creation.

