<div id="pageContent" style="display:none"></div>
<table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%" style="height:100%">
    <tr>
        <td style="background:url('images/bg-topbar.gif');background-position:0px 2px" height="85" width="100%">
            <table border="0" cellpadding="0" cellspacing="0" width="100%" height="85">
                <tr>
                    <td width="25" height="85" style="background:url('images/rivets_topleft.gif');background-position:left 2px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    </td>
                    <td width="100%" style="padding:0;background-image:url('images/bg-topbar.gif');background-position:0px 2px;"><img src="images/logo.png" width="380" height="85" border="0" style="position:absolute;top:3px"/>
                        <span style="position:absolute;top:33px;left:153px">
                            <span id="incoming"><a href="Notifications.act?action=seeIncoming"><img src="images/incoming.png" width="32" height="32" alt="Voir notifications" style="border:0" /></a></span>
                            <span id="outgoing"><a href="Notifications.act?action=seeOutgoing"><img src="images/outgoing.png" width="32" height="32" alt="Voir vos actions" style="border:0"/></a></span>
                        </span>
                    </td>
                    <td width="537">
                        <table border="0" cellpadding="0" cellspacing="0" width="537" height="85" style="background-image:url('images/bg-cutout.gif');background-position:0px 2px">
                            <tr><td style="background-image:url('images/cutout-left.gif');background-repeat:no-repeat;background-position:right 2px">&nbsp;&nbsp;&nbsp;
                                </td>
                                <td width="522">
                                    <table border="0" cellpadding="0" cellspacing="0" width="522" height="85">
                                        <tr>
                                            <td width="522" height="28" style="background-image:url('images/hd_1.png');background-position:0px 2px;padding-top:20px">
                                                <div id="dewplayer_content">&nbsp;
                                                </div>
                                                <div id="login">Login ?</div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td width="522" height="39">
                                                <table border="0" cellpadding="0" cellspacing="0" width="100%">
                                                    <tr>
                                                        <td align="center">
                                                            <a href="UI.act?nocanvas=1" onMouseover="img_act('nav1')" onMouseout="img_inact('nav1')">
                                                                <img src="images/bt_1.png" width="103" height="39" border="0" name="nav1">
                                                            </a>
                                                        </td>
                                                        <td align="center">
                                                            <img src="images/bt_dv.gif" width="2" height="39" border="0">
                                                        </td>
                                                        <td align="center">
                                                            <a href="javascript:toggleMenu ('menuAjouter')" onMouseover="img_act('nav2')" onMouseout="img_inact('nav2')">
                                                                <img src="images/bt_2.png" width="103" height="39" border="0" name="nav2">
                                                            </a>
                                                            <script type="text/javascript">menus.push ("menuAjouter");</script>
                                                            <div dojoType="dijit.Menu" id="menuAjouter" contextMenuForWindow="true" style="display:none;position:absolute;margin-top:-26px">
                                                                <div dojoType="dijit.MenuItem" onClick="javascript:go('menuAjouter', 'jsp/upload/ajouterMusique.jsp')">Musique&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
                                                                <div dojoType="dijit.MenuItem" onClick="javascript:go('menuAjouter', 'jsp/upload/ajouterVideo.jsp')">Bande son d'une video</div>
                                                                <div dojoType="dijit.MenuItem" onClick="javascript:go('menuAjouter', 'jsp/playlist/creerNomPlaylist.jsp')">Playlist</div>
                                                            </div>
                                                        </td>
                                                        <td align="center">
                                                            <img src="images/bt_dv.gif" width="2" height="39" border="0">
                                                        </td>
                                                        <td align="center">
                                                            <a href="javascript:toggleMenu ('menuChercher')"  onMouseover="img_act('nav3')" onMouseout="img_inact('nav3')">
                                                                <img src="images/bt_3.png" width="103" height="39" border="0" name="nav3">
                                                            </a>
                                                            <script type="text/javascript">menus.push ("menuChercher");</script>
                                                            <form name="searchForm">
                                                                <div dojoType="dijit.Menu" id="menuChercher" contextMenuForWindow="true" style="display:none;position:absolute;margin-top:-26px">
                                                                    <div dojoType="dijit.MenuItem"><label for="query">Chercher</label><input dojoType="dijit.form.TextBox" type="text" name="query"/>
                                                                        <button dojoType="dijit.form.Button" type="button" onclick="javascript:chercher (document.forms ['searchForm'].query.value)">&gt;</button></div>
                                                                </div>
                                                            </form>
                                                        </td>
                                                        <td align="center">
                                                            <img src="images/bt_dv.gif" width="2" height="39" border="0">
                                                        </td>
                                                        <td align="center">
                                                            <a href="javascript:toggleMenu ('menuInvitations')" onMouseover="img_act('nav4')" onMouseout="img_inact('nav4')">
                                                                <img src="images/bt_4.png" width="103" height="39" border="0" name="nav4">
                                                            </a>
                                                            <script type="text/javascript">menus.push ("menuInvitations");</script>                                                                    <script type="text/javascript">menus.push ("menuAjouter");</script>
                                                            <div dojoType="dijit.Menu" id="menuInvitations" contextMenuForWindow="true" style="display:none;position:absolute;margin-top:-26px">
                                                                <div dojoType="dijit.MenuItem" onClick="javascript:go('menuInvitations', 'Invite.act?action=voirPropoPopup')">Mes propositions</div>
                                                                <div dojoType="dijit.MenuItem" onClick="javascript:go('menuInvitations', 'Invite.act?action=voirInvitPopup')">Mes invitations</div>
                                                                <div dojoType="dijit.MenuItem" onClick="javascript:go('menuInvitations', 'Invite.act?action=proposerPopup')">Inviter</div>
                                                            </div>
                                                        </td>
                                                        <td align="center">
                                                            <img src="images/bt_dv.gif" width="2" height="39" border="0">
                                                        </td>
                                                        <td align="center">
                                                            <a href="jsp/aide/apropos.jsp" onMouseover="img_act('nav5')" onMouseout="img_inact('nav5')">
                                                                <img src="images/bt_5.png" width="103" height="39" border="0" name="nav5">
                                                            </a>
                                                        </td>
                                                    </tr>
                                                </table>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                                <td width="23" height="85" style="background-image:url('images/cutout-right.gif');background-position:left 2px;background-repeat:no-repeat;">&nbsp;&nbsp;&nbsp;
                                </td>
                            </tr>
                        </table>
                    </td>
                    <td width="28" style="background-image:url('images/rivets_topright.gif');background-position:right 2px;background-repeat:no-repeat">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>