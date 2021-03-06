package com.funrisestudio.mvimessenger.ui.dialogs

import androidx.compose.Composable
import androidx.ui.core.Modifier
import androidx.ui.core.clip
import androidx.ui.core.tag
import androidx.ui.foundation.*
import androidx.ui.foundation.shape.corner.CircleShape
import androidx.ui.graphics.Color
import androidx.ui.layout.*
import androidx.ui.res.imageResource
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import com.funrisestudio.mvimessenger.ui.*

@Composable
fun DialogListItem(item: DialogViewData, onClick: (DialogViewData) -> Unit) {
    val dgData = item.dialog
    val avatarAsset = imageResource(dgData.contact.avatar)
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = {
                onClick(item)
            })
            .padding(paddingL),
        constraintSet = ConstraintSet2 {

            val ivAvatar = createRefFor("ivAvatar")
            val tvSender = createRefFor("tvSender")
            val tvLastMessage = createRefFor("tvLastMessage")
            val tvLastMessageTime = createRefFor("tvLastMessageTime")
            val tvUnreadCountHolder = createRefFor("tvUnreadCountHolder")

            constrain(ivAvatar) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            }
            constrain(tvSender) {
                start.linkTo(ivAvatar.end)
                top.linkTo(parent.top)
            }
            constrain(tvLastMessage) {
                start.linkTo(ivAvatar.end)
                bottom.linkTo(parent.bottom)
            }
            constrain(tvLastMessageTime) {
                top.linkTo(tvSender.top)
                bottom.linkTo(tvSender.bottom)
                end.linkTo(parent.end)
            }
            constrain(tvUnreadCountHolder) {
                top.linkTo(tvLastMessageTime.bottom)
                bottom.linkTo(parent.bottom)
                end.linkTo(parent.end)
            }

            createVerticalChain(
                tvSender,
                tvLastMessage,
                chainStyle = ChainStyle.Packed
            )
        }
    ) {
        Image(
            asset = avatarAsset,
            modifier = Modifier
                .tag("ivAvatar")
                .size(48.dp)
                .clip(shape = CircleShape)
        )
        Text(
            text = dgData.contact.name,
            modifier = Modifier
                .tag("tvSender")
                .padding(start = paddingL),
            style = typography.body1
        )
        Text(
            text = dgData.lastMessage.text,
            modifier = Modifier
                .tag("tvLastMessage")
                .padding(start = paddingL, top = paddingS),
            style = typography.body2
        )
        Text(
            text = item.formattedDate,
            modifier = Modifier
                .tag("tvLastMessageTime"),
            style = typography.caption
        )
        if (dgData.unreadCount != 0) {
            Box(
                shape = CircleShape,
                backgroundColor = colorAccent,
                modifier = Modifier
                    .tag("tvUnreadCountHolder")
                    .size(24.dp),
                gravity = ContentGravity.Center
            ) {
                Text(
                    text = dgData.unreadCount.toString(),
                    style = typography.body2.copy(color = Color.White)
                )
            }
        } else {
            //empty view
            Column(modifier = Modifier.tag("tvUnreadCountHolder")) {}
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DialogListItemPreview() {
    AppTheme {
        DialogListItem(
            getFakeDialogViewData()[0],
            onClick = {

            }
        )
    }
}