# hw_android_2022-2023


<table style="width:100%; border-collapse:separate;">


        <tr>
            <td class="labels">
                <dx:ASPxLabel runat="server" Text="Исполнитель:" Wrap="False" />
            </td>
            <td>
                <uc:ButtonEdit ID="BeExecutorNo" ClientInstanceName="beExecutorNo"
                    runat="server" Width="100%">
                    <HandbookSettings>
                        <hs:HandbookSetting TypeNo="111" />
                    </HandbookSettings>
                </uc:ButtonEdit>
            </td>
        </tr>
        <tr>
            <td class="labels">
                <dx:ASPxLabel runat="server" Text="Описание:" Wrap="False" />
            </td>


            <td>
                <dx:ASPxTextBox runat="server" ClientInstanceName="tbDescription" ID="TbDescription" Width="100%" />
            </td>
        </tr>
      
        <tr>
            <td class="labels">
                <dx:ASPxLabel ID="LbCreationDate" runat="server" Text="&nbsp;Дата создания" />
            </td>
            <%--Дата создания--%>
            <td>
                <dx:ASPxDateEdit ID="DeCreationDate" ClientInstanceName="deCreationDate" AutoPostBack="false" runat="server" Width="100%" ClientEnabled="false">
                </dx:ASPxDateEdit>
            </td>
        </tr>
        <tr>
            <td class="labels">
                <dx:ASPxLabel ID="LbChangeDate" runat="server" Text="&nbsp;Дата изменения" />
            </td>
            <%--Дата изменения--%>
            <td>
                <dx:ASPxDateEdit ID="DeChangeDate" ClientInstanceName="deChangeDate" AutoPostBack="false" runat="server" Width="100%" ClientEnabled="false">
                </dx:ASPxDateEdit>
            </td>
        </tr>
        <tr>
            <td class="labels">
                <dx:ASPxLabel ID="LbMaxYear" runat="server" Text="Максимальный год" />
            </td>

            <td>
                <dx:ASPxSpinEdit ID="SeMaxYear" ClientInstanceName="seMaxYear" runat="server" Width="100%" ClientEnabled="false" />
            </td>
        </tr>
    </table>
