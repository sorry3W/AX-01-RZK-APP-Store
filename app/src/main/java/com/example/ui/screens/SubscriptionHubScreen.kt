package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Sms
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.LegalContent
import com.example.data.local.entity.SubscriptionEntity
import com.example.model.SubscriptionTier
import com.example.ui.components.CyberPill
import com.example.ui.theme.CyberBorder
import com.example.ui.theme.CyberCard
import com.example.ui.theme.CyberCardElevated
import com.example.ui.theme.CyberDarkBg
import com.example.ui.theme.CyberSurface
import com.example.ui.theme.NeonAmber
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.NeonEmerald
import com.example.ui.theme.NeonPurple
import com.example.ui.theme.NeonRose
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun SubscriptionHubScreen(
    currentSubscription: SubscriptionEntity?,
    onActivatePlan: (tier: SubscriptionTier, method: String, txId: String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var selectedTierForCheckout by remember { mutableStateOf<SubscriptionTier?>(null) }

    fun copyToClipboard(label: String, text: String) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(label, text)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(context, "Copied $label: $text", Toast.LENGTH_SHORT).show()
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 14.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Active Status Banner
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = CyberCardElevated),
                border = BorderStroke(1.dp, NeonAmber.copy(alpha = 0.8f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.WorkspacePremium, contentDescription = null, tint = NeonAmber, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                "CURRENT SUBSCRIPTION STATUS",
                                color = NeonAmber,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                fontFamily = FontFamily.Monospace
                            )
                        }

                        CyberPill(text = "ACTIVE", color = NeonEmerald)
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    val planName = currentSubscription?.planName ?: "Trial Sandbox (60 Days)"
                    val method = currentSubscription?.paymentMethod ?: "Evaluation License"
                    val expiresFormatted = currentSubscription?.let {
                        SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(Date(it.expiresAt))
                    } ?: "60 Days Remaining"

                    Text(planName, color = TextPrimary, fontSize = 20.sp, fontWeight = FontWeight.Black)
                    Spacer(modifier = Modifier.height(2.dp))
                    Text("Settlement: $method • Valid Until: $expiresFormatted", color = TextSecondary, fontSize = 11.sp, fontFamily = FontFamily.Monospace)

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        LegalContent.QUANTUM_PROMISE,
                        color = NeonCyan,
                        fontSize = 10.sp,
                        fontFamily = FontFamily.Monospace
                    )
                }
            }
        }

        // Official Payment Gateways Card (from PDF)
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = CyberCard),
                border = BorderStroke(1.dp, CyberBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        "OFFICIAL PAYMENT CHANNELS & SYSTEMS",
                        color = TextSecondary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    // BKash / Nagad
                    PaymentChannelRow(
                        icon = Icons.Default.PhoneAndroid,
                        title = "BKash / Nagad Direct",
                        detail = LegalContent.BKASH_NAGAD,
                        color = NeonRose,
                        onCopy = { copyToClipboard("bKash/Nagad", LegalContent.BKASH_NAGAD) }
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Mastercard
                    PaymentChannelRow(
                        icon = Icons.Default.CreditCard,
                        title = "Mastercard Payment System",
                        detail = LegalContent.MASTERCARD_SYSTEM,
                        color = NeonAmber,
                        onCopy = { copyToClipboard("Mastercard", LegalContent.MASTERCARD_SYSTEM) }
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Direct SMS
                    PaymentChannelRow(
                        icon = Icons.Default.Sms,
                        title = "Direct SMS Activation",
                        detail = LegalContent.DIRECT_SMS,
                        color = NeonCyan,
                        onCopy = { copyToClipboard("Direct SMS", LegalContent.DIRECT_SMS) }
                    )
                }
            }
        }

        // Section Title: Available Plans
        item {
            Text(
                "AX-01 SUBSCRIPTION & PRICING TIERS",
                color = TextPrimary,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace
            )
        }

        // Pricing Cards
        items(SubscriptionTier.entries) { tier ->
            val isCurrent = currentSubscription?.tierId == tier.tierId || (currentSubscription == null && tier == SubscriptionTier.TRIAL_60_DAYS)
            val badgeColor = Color(tier.badgeColorHex)

            Card(
                colors = CardDefaults.cardColors(containerColor = if (isCurrent) CyberCardElevated else CyberCard),
                border = BorderStroke(if (isCurrent) 2.dp else 1.dp, if (isCurrent) badgeColor else CyberBorder),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth().testTag("plan_card_${tier.tierId}")
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(tier.title, color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        if (tier.isPopular) {
                            CyberPill(text = "RECOMMENDED", color = NeonPurple)
                        } else if (isCurrent) {
                            CyberPill(text = "CURRENT PLAN", color = NeonEmerald)
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(verticalAlignment = Alignment.Bottom) {
                        Text(
                            text = tier.priceBdtFormatted,
                            color = badgeColor,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Black,
                            fontFamily = FontFamily.Monospace
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "/ ${tier.durationText}",
                            color = TextMuted,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(bottom = 3.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))
                    Text(tier.description, color = TextSecondary, fontSize = 12.sp)

                    Spacer(modifier = Modifier.height(10.dp))
                    tier.perks.forEach { perk ->
                        Row(
                            modifier = Modifier.padding(vertical = 2.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.Check, contentDescription = null, tint = badgeColor, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(perk, color = TextPrimary, fontSize = 11.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Button(
                        onClick = { selectedTierForCheckout = tier },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isCurrent) CyberSurface else badgeColor,
                            contentColor = if (isCurrent) TextPrimary else Color.Black
                        ),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth().height(42.dp).testTag("select_tier_${tier.tierId}")
                    ) {
                        Text(
                            text = if (isCurrent) "RENEW / EXTEND TIER" else "SUBSCRIBE (${tier.priceBdtFormatted})",
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp
                        )
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(20.dp))
        }
    }

    // Checkout / Activation Dialog
    selectedTierForCheckout?.let { tier ->
        CheckoutPaymentDialog(
            tier = tier,
            onDismiss = { selectedTierForCheckout = null },
            onConfirmPayment = { method, txId ->
                onActivatePlan(tier, method, txId)
                selectedTierForCheckout = null
                Toast.makeText(context, "Activated ${tier.title} successfully!", Toast.LENGTH_LONG).show()
            }
        )
    }
}

@Composable
fun PaymentChannelRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    detail: String,
    color: Color,
    onCopy: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(CyberSurface)
            .border(1.dp, CyberBorder, RoundedCornerShape(8.dp))
            .padding(horizontal = 10.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(title, color = TextSecondary, fontSize = 10.sp)
                Text(detail, color = TextPrimary, fontSize = 12.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
            }
        }

        IconButton(onClick = onCopy, modifier = Modifier.size(30.dp)) {
            Icon(Icons.Default.ContentCopy, contentDescription = "Copy", tint = NeonCyan, modifier = Modifier.size(15.dp))
        }
    }
}

@Composable
fun CheckoutPaymentDialog(
    tier: SubscriptionTier,
    onDismiss: () -> Unit,
    onConfirmPayment: (method: String, txId: String) -> Unit
) {
    var selectedMethod by remember { mutableStateOf("bKash/Nagad (01640646714)") }
    var transactionIdInput by remember { mutableStateOf("TXN-${(100000..999999).random()}") }

    val methods = listOf(
        "bKash/Nagad (01640646714)",
        "Mastercard System (5191555506390948)",
        "Direct SMS Activation (01990761212)"
    )

    Dialog(onDismissRequest = onDismiss) {
        Card(
            colors = CardDefaults.cardColors(containerColor = CyberDarkBg),
            border = BorderStroke(1.dp, NeonCyan),
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier.fillMaxWidth().padding(14.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "ACTIVATE SUBSCRIPTION",
                    color = NeonCyan,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${tier.title} • ${tier.priceBdtFormatted}",
                    color = TextPrimary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Black
                )

                Spacer(modifier = Modifier.height(14.dp))
                Text("Select Payment Gateway:", color = TextSecondary, fontSize = 11.sp)
                Spacer(modifier = Modifier.height(6.dp))

                methods.forEach { meth ->
                    val isSel = selectedMethod == meth
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(6.dp))
                            .background(if (isSel) CyberCardElevated else CyberSurface)
                            .border(1.dp, if (isSel) NeonCyan else CyberBorder, RoundedCornerShape(6.dp))
                            .clickable { selectedMethod = meth }
                            .padding(horizontal = 10.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(meth, color = if (isSel) NeonCyan else TextPrimary, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                        if (isSel) {
                            Icon(Icons.Default.Check, contentDescription = null, tint = NeonCyan, modifier = Modifier.size(16.dp))
                        }
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                }

                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = transactionIdInput,
                    onValueChange = { transactionIdInput = it },
                    label = { Text("Transaction Reference ID", fontSize = 11.sp) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = NeonCyan,
                        unfocusedBorderColor = CyberBorder,
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary
                    )
                )

                Spacer(modifier = Modifier.height(14.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Cancel", color = TextMuted)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            onConfirmPayment(selectedMethod, transactionIdInput.trim())
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = NeonEmerald, contentColor = Color.Black)
                    ) {
                        Text("CONFIRM ACTIVATION", fontWeight = FontWeight.Bold, fontSize = 11.sp)
                    }
                }
            }
        }
    }
}
