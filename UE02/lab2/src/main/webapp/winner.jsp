<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="de" lang="de">
    <head>
        <meta charset="utf-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Business Informatics Group Jeopardy! - Gewinnanzeige</title>
        <link rel="stylesheet" type="text/css" href="style/base.css" />
        <link rel="stylesheet" type="text/css" href="style/screen.css" />
		  <script src="js/jquery.js" type="text/javascript"></script>
        <script src="js/framework.js" type="text/javascript"></script>
    </head>
    <body id="winner-page">
      <a class="accessibility" href="#winner">Zur Gewinnanzeige springen</a>
      <!-- Header -->
      <header role="banner" aria-labelledby="bannerheading">
         <h1 id="bannerheading">
            <span class="accessibility">Business Informatics Group </span><span class="gametitle">Jeopardy!</span>
         </h1>
      </header>
      
      <!-- Navigation -->
		<nav role="navigation" aria-labelledby="navheading">
			<h2 id="navheading" class="accessibility">Navigation</h2>
			<ul>
				<li><a class="orangelink navigationlink" id="logoutlink" title="Klicke hier um dich abzumelden" href="login.jsp" accesskey="l">Abmelden</a></li>
			</ul>
		</nav>
      
      <!-- Content -->
      <div role="main">
      	<jsp:useBean id="user" scope="session" type="at.ac.tuwien.big.we15.lab2.api.User" />
        <jsp:useBean id="bot" scope="session" type="at.ac.tuwien.big.we15.lab2.api.Bot" />
         <section id="gameinfo" aria-labelledby="winnerinfoheading">
            <h2 id="winnerinfoheading" class="accessibility">Gewinnerinformationen</h2>
            
            <% if(user.getLastProfit() > 0) { %>
            	<p class="user-info positive-change">Du hast richtig geantwortet: +<%=user.getLastProfit() %> €</p>
            <% } else if(user.getLastProfit() < 0) { %>
            	<p class="user-info negative-change">Du hast falsch geantwortet: <%=user.getLastProfit() %> €</p>
            <% } %>
            
            <% if(bot.getLastProfit() > 0) { %>
            	<p class="user-info positive-change"><%=bot.getUsername() %> hat richtig geantwortet: +<%=bot.getLastProfit() %> €</p>
            <% } else if(bot.getLastProfit() < 0) { %>
            	<p class="user-info negative-change"><%=bot.getUsername() %> hat falsch geantwortet: <%=bot.getLastProfit() %> €</p>
            <% } %>
            
            <!-- <p class="user-info positive-change">Du hast richtig geantwortet: +1000 €</p>
            <p class="user-info negative-change">Deadpool hat falsch geantwortet: -500 €</p> -->
            
            <jsp:useBean id="winner" scope="session" type="at.ac.tuwien.big.we15.lab2.api.User" />
            <section class="playerinfo leader" aria-labelledby="winnerannouncement">
               <h3 id="winnerannouncement">Gewinner: <%=winner.getUsername() %></h3>
               <img class="avatar" src="<%=winner.getImage() %>" alt="Spieler-Avatar Black Widow" />
               <table>
                  <tr>
                     <th class="accessibility">Spielername</th>
                     <td class="playername"><%=winner.getUsername() %></td>
                  </tr>
                  <tr>
                     <th class="accessibility">Spielerpunkte</th>
                     <td class="playerpoints">€ <%=winner.getSum() %></td>
                  </tr>
               </table>
            </section>
            <jsp:useBean id="loser" scope="session" type="at.ac.tuwien.big.we15.lab2.api.User" />
            <section class="playerinfo" aria-labelledby="loserheading">
               <h3 id="loserheading" class="accessibility">Verlierer: Deadpool</h3>
               <img class="avatar" src="<%=loser.getImageHead() %>" alt="Spieler-Avatar Deadpool" />
               <table>
                  <tr>
                     <th class="accessibility">Spielername</th>
                     <td class="playername"><%=loser.getUsername() %></td>
                  </tr>
                  <tr>
                     <th class="accessibility">Spielerpunkte</th>
                     <td class="playerpoints">€ <%=loser.getSum() %></td>
                  </tr>
               </table>
            </section>
         </section>
         <section id="newgame" aria-labelledby="newgameheading">
             <h2 id="newgameheading" class="accessibility">Neues Spiel</h2>
         	<form action="BigJeopardyServlet" method="post">
               	<input class="clickable orangelink contentlink" id="new_game" type="submit" name="restart" value="Neues Spiel" />
            </form>
         </section>
      </div>
        <!-- footer -->
        <footer role="contentinfo">© 2015 BIG Jeopardy</footer>  
		<script type="text/javascript">
        //<![CDATA[
           $(document).ready(function(){
         	   if(supportsLocalStorage()){
         		   localStorage["lastGame"] = new Date().getTime();
         	   }
           });
        //]]>
        </script>  
    </body>
</html>
