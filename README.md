# Vince Earl Liam Bot

Manage your role games easy with the help of the best right-hand ~~man~~ bot.  
Vince Earl Liam handles boring tasks like tracking of next scheduled game as well as remember to your players what THEY have done in previous sessions.  
He is inspired to [Niccolò Machiavelli](https://en.wikipedia.org/wiki/Niccol%C3%B2_Machiavelli) and so ... why don't make him speak in Italian only? XD

## Pointments

**telegram username:** @VinceEarlLiamBot  
**message endpoint:** https://master-manager.herokuapp.com/f29ca76cdbaec82d5819ce9f4a52773f/updates

## Available commands
Private commands are reserved to Game Master and so they aren't declared in Telegram chat.

### Private commands

 - /set_sessione dd/MM/yyyy HH:mm → set next game session (yes, input date is in Italian format)

### Public commands

 - /sessione → get next game session or a [default message](https://translate.google.it/?hl=it&sl=it&tl=en&text=Prossima%20sessione%3A%20non%20settata&op=translate) if the scheduled date is expired or unset 
