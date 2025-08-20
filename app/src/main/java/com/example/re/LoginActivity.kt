package com.example.re

import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.re.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LoginActivity : AppCompatActivity() {


    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    private lateinit var firstNameField: EditText
    private lateinit var lastNameField: EditText
    private lateinit var emailField: EditText
    private lateinit var passwordField: EditText
    private lateinit var confirmPasswordField: EditText
    private lateinit var countrySpinner: Spinner
    private lateinit var stateSpinner: Spinner
    private lateinit var citySpinner: Spinner
    private lateinit var localitySpinner: Spinner
    private lateinit var showPasswordButton: ImageView
    private lateinit var showConfirmPasswordButton: ImageView
    private lateinit var loadingIndicator: ProgressBar
    private val countries = mapOf(
        "India" to mapOf(
            "Maharashtra" to mapOf(
                "Mumbai" to listOf("Colaba", "Bandra", "Andheri", "Juhu", "Nariman Point", "Malad", "Goregaon", "Thane", "Powai", "Lower Parel", "Fort", "Dadar", "Worli", "Versova", "Borivali", "Kurla", "Churchgate"),
                "Pune" to listOf("Camp", "Koregaon Park", "Hinjewadi", "Viman Nagar", "Shivajinagar", "Pimpri", "Chinchwad", "Kothrud", "Bavdhan", "Wakad", "Bhosari", "Hadapsar", "Karve Nagar", "Swargate", "Deccan Gymkhana"),
                "Nagpur" to listOf("Sitabuldi", "Cotton Market", "Civil Lines", "Gadital", "Jaripatka", "Hingana", "Khamla", "Nandanvan", "Mhalgi Nagar", "Manish Nagar", "Wadi", "Dighori", "Ajni", "Nehrunagar", "Kalamna"),
                "Nashik" to listOf("Peth Road", "Mhasrul", "Gangapur", "CIDCO", "Makhmalabad", "Sharanpur", "College Road", "Kumar Colony", "Indira Nagar", "Pathardi", "Saptashrungi", "Jail Road", "Sula Vineyard", "Panchavati"),
                "Aurangabad" to listOf("Cidco", "N-2", "Mankapura", "Ranjangaon", "Adgaon", "Jalna Road", "Aurangabad City", "Waluj", "Paithan", "Garkheda", "Khandala", "Mukundwadi", "Chilkalthana", "Harsul"),
                "Solapur" to listOf("Akkalkot Road", "Solapur Market", "Jai Bhavani", "Mangalwedha", "Siddheshwar Temple Area", "Murarji Peth", "Karmala", "Vikramgad", "Raviraj Complex", "Dattawadi", "Siddheshwar", "Muktai Nagar", "Ramswami"),
                "Kolhapur" to listOf("Shivaji University", "Panchgani", "Tarabai Park", "Mahalaxmi", "Rajarampuri", "Ayodhya Nagar", "Shahupuri", "Kasba Bavada", "Bambavade", "Narsobawadi", "Laxmipuri", "Gadad", "Vithalwadi"),
                "Thane" to listOf("Thane West", "Thane East", "Kopar", "Vasai", "Virar", "Dombivli", "Kalyan", "Bhiwandi", "Mira Road", "Bhakti Park", "Ghodbunder Road", "Wagle Estate", "Kasarvadavali", "Teen Hath Naka"),
                "Latur" to listOf("Bhalki", "Hugargaon", "Latur City", "Nilanga", "Vashi", "Renapur", "Vambori", "Ausa", "Sillod", "Ghataprabha", "Alandi", "Nanded Road", "Karmad", "Kondapalli"),
                "Jalna" to listOf("Kalyan", "Malkapur", "Gulshan Nagar", "Siddharth Nagar", "Ravi Nagar", "Ranjani", "Malkapur Road", "Agar", "Tirupati Nagar", "Chikhali", "Sanjivani", "Dhotre", "Ghodegaon", "Jalna City")
            ),
            "Delhi" to mapOf(
                "Delhi" to mapOf(
                    "Central Delhi" to listOf("Connaught Place", "Rajiv Chowk", "India Gate", "Janpath", "Paharganj", "Chandni Chowk", "Kashmiri Gate", "Nai Sarak", "Karol Bagh", "Jhandewalan", "Sadar Bazar", "Daryaganj", "Ranjit Singh Flyover"),
                    "North Delhi" to listOf("Model Town", "Ashok Vihar", "North Campus", "Bawana", "Rohini", "Pitampura", "Shalimar Bagh", "Kanjhawala", "Narela", "Nangloi", "Sanjay Gandhi Transport Nagar", "Burari", "Prem Nagar"),
                    "South Delhi" to listOf("Hauz Khas", "Green Park", "Lajpat Nagar", "Kalkaji", "Greater Kailash", "Mehrauli", "Vasant Kunj", "Saket", "Chattarpur", "Defence Colony", "C.R. Park", "Pushp Vihar", "Okhla", "Safdarjung Enclave"),
                    "West Delhi" to listOf("Rajouri Garden", "Dwarka", "Janakpuri", "Vikaspuri", "Uttam Nagar", "Subhash Nagar", "Mundka", "Punjabi Bagh", "Hari Nagar", "Nangloi", "Tagore Garden", "Sarai Rohilla", "Sanjay Gandhi Transport Nagar", "Pankha Road"),
                    "East Delhi" to listOf("Preet Vihar", "Mayur Vihar", "Laxmi Nagar", "Patparganj", "Vikram Vihar", "Kalkaji Extension", "Sarai Kale Khan", "Mandawali", "Noida", "Vasundhara", "Chand Nagar", "Harkesh Nagar", "Gandhi Nagar", "Shahdara"),
                    "North West Delhi" to listOf("Shalimar Bagh", "Rohini", "Pitampura", "Narela", "Rani Bagh", "Prashant Vihar", "Mundka", "Kanjhawala", "Bawana", "Keshav Puram", "Gurunanak Pura", "Kalkaji Extension", "Jahangirpuri", "Rohini Sector 9"),
                    "South West Delhi" to listOf("Dwarka", "Vasant Kunj", "Saket", "Mehrauli", "Janakpuri", "Uttam Nagar", "Kakrola", "Nangloi", "Najafgarh", "Mahavir Enclave", "Raj Nagar", "Rohini Sector 16", "Bijwasan", "Nangloi"),
                    "Central Delhi" to listOf("Connaught Place", "Rajiv Chowk", "Chandni Chowk", "Karol Bagh", "Paharganj", "Sadar Bazar", "India Gate", "Nai Sarak", "Daryaganj", "Jhandewalan", "Nai Sarak", "Ranjit Singh Flyover", "Pragati Maidan", "Raisina Hill")
                )
            ),
            "Uttar Pradesh" to mapOf(
                "Varanasi" to listOf("Sarnath","Ganga Ghaat","Lanka","Kashi Vishwanath Temple Area","Dasaswamedh Ghat","Assi Ghat","Manikarnika Ghat","Trilochan Mahadev"),
                "Agra" to listOf("Taj Mahal Area", "Agra Fort", "Mehtab Bagh", "Sikandra", "Idgah Area", "Khandari", "Balkeshwar", "Dayalbagh", "Civil Lines", "Mankameshwar", "Pratap Pura", "Raja Mandi", "Shahganj", "Sadar Bazar", "Kashmiri Bazar", "Pachokhara", "Taj Ganj"),
                "Ayodhya" to listOf("Ram Janmabhoomi", "Hanuman Garhi", "Kanak Bhawan", "Digambar Jain Mandir", "Choti Chaupad", "Guptar Ghat", "KalpVriksha", "Swarg Dwar", "Raja Mandir", "Shri Ram Path", "Treta Ke Thakur", "Mani Parvat", "Saryu River Bank", "Valmiki Ashram", "Janki Mahal", "Rama Ki Paidi", "Anand Bhawan"),
                "Gautam Buddha Nagar(Noida)" to listOf("Sector 18", "Sector 50", "Sector 62", "Sector 15A", "Sector 27", "Sector 32", "Sector 44", "Sector 56", "Sector 137", "Sector 125", "Noida City Centre", "Noida Extension", "Sector 71", "Sector 120", "Sector 126", "Sector 128", "Sector 134", "Sector 135"),
                "Gorakhpur" to listOf("Golghar", "Rohini", "Kushinagar Road", "Vikramshila", "Buxipur", "Gorakhnath Temple Area", "Nai Basti", "Kaptanganj", "Rajendra Nagar", "Mau Road", "Maharajganj", "Cantt Area", "Lal Diggi", "Shivpur", "Gopalpur", "Hassanpur", "Gida"),
                "Mathura" to listOf("Krishna Janmabhoomi", "Vishram Ghat", "Govardhan", "Mathura-Vrindavan Road", "Chowk", "Dampier Nagar", "Madhavpuri", "Raja Mandir", "Baldeo", "Sarai Khwaja", "Gokul", "Nandgaon", "Barsana", "Raja Ki Mandi", "Pipli", "Birla Mandir", "Sadar Bazar"),
                "Kanpur Nagar" to listOf("Kanpur Cantonment", "Gumti No. 5", "Kalyanpur", "Civil Lines", "Nawabganj", "Chaupati", "Swaroop Nagar", "Narendra Nagar", "Rajpur", "Panki", "Kidwai Nagar", "Shivli", "Jajmau", "Bithoor", "Cantonment Area", "Harsh Nagar", "Azad Nagar"),
                "Lucknow" to listOf("Hazratganj", "Alambagh", "Indira Nagar", "Gomti Nagar", "Kakori", "Mahanagar", "Jankipuram", "Vikramaditya Marg", "Chowk", "Sadar", "Dargah Sharif", "Aminabad", "Hussainganj", "Kanpur Road", "Bada Mangal", "Rajajipuram", "Vikramshila"),
                "Prayagraj" to listOf("Triveni Sangam", "Civil Lines", "Kashmiri Mohalla", "Mau Aima", "Naini", "Daraganj", "Sulem Sarai", "George Town", "Pratapgarh", "Phaphamau", "Allahabad University Area", "Chowk", "Sadar", "Dhoomanganj", "Tagore Town", "Rajrooppur", "Rambagh"),
                "Jhansi" to listOf("Jhansi Fort Area", "Rani Jhansi Road", "Laxmi Complex", "Niralanagar", "Gwalior Road", "Kachari", "Maharani Laxmi Bai Colony", "Sadar Bazar", "Civil Lines", "Shivaji Nagar", "Subhash Ganj", "Gurukunj", "Indira Nagar", "Raghunathpura", "Puranpura", "Harpur"),
                "Sant Kabir Nagar" to listOf("Kadipur", "Dhanghata", "Khalilabad", "Naugarh", "Pipraich", "Bariyarpur", "Bhitauli", "Tanda", "Siddharthnagar", "Brijpur", "Chhapia", "Ramnagar", "Bhelsa", "Dewaria", "Kachhwa", "Gorsari"),
                "Meerut" to listOf("Shastri Nagar", "Kanker Khera", "Partapur", "Garh Road", "Meerut Cantt", "Hapur Road", "Delhi Road", "Sardhana", "Mawana", "Brahmpuri", "Vijay Nagar", "Surajkund", "Rajender Nagar", "Mangal Pandey Nagar", "Jaswant Vihar", "Avas Vikas", "Maviya Nagar"),

                ),
            "Rajasthan" to mapOf(
                "Jaipur" to listOf("C-Scheme", "Bapu Nagar", "Mansarovar", "Malviya Nagar", "Vaishali Nagar", "Raja Park", "Ashok Nagar", "Sanganer", "JLN Marg", "Gopalpura Bypass", "Shastri Nagar", "Chandpole", "Civil Lines", "Vidhyadhar Nagar", "Ramganj"),
                "Udaipur" to listOf("City Palace", "Lake Pichola", "Hiran Magri", "Saheliyon-ki-Bari", "Bhopalpura", "Raja Park", "Shiksha Mandal", "Bhuwana", "Sector 11", "Sector 9", "Nehru Garden", "Pratap Nagar", "Sukhadia Circle", "Goverdhan Vilas"),
                "Jodhpur" to listOf("Mehrangarh Fort", "Clock Tower", "Umaid Bhawan Palace", "Sojati Gate", "Ratanada", "Pal Road", "Mandore", "Ratanada", "Basni", "Shivaji Circle", "Sardar Market", "Jhalamand", "Pali Road"),
                "Kota" to listOf("Talwandi", "Railway Station Area", "Vijaypura", "Pratap Nagar", "Gumanpura", "Chambal Garden", "Dhanmandi", "Sangod", "Shastri Nagar", "Kota University Area", "Jhalawar Road", "Rajasthan Vidyapeeth", "Indra Vihar"),
                "Bikaner" to listOf("Junagarh Fort", "Kote Gate", "Lalgarh Palace", "Madhuban", "Nabik", "Rani Bazar", "Bhagwati Nagar", "Shastri Nagar", "Ganga Singh Nagar", "Sardulgarh", "Panchsati Circle", "Karmisar", "Rajasthan University Area"),
                "Ajmer" to listOf("Ajmer Sharif Dargah", "Ana Sagar Lake", "Taragarh Fort", "Civil Lines", "Vaishali Nagar", "Raja Park", "JLN Marg", "Ravi Nagar", "Nehru Garden", "Ganj Area", "Dargah Bazar", "Chandpole", "Rohat"),
                "Alwar" to listOf("Alwar Fort", "Sariska Tiger Reserve", "Raja Park", "Bharatpur Road", "Moti Doongri", "Gokulgarh", "Aligarh", "Krishna Colony", "Mewat", "Civil Lines", "Dudhwa", "Bharatpur Road"),
                "Sikar" to listOf("Rani Sati Temple", "Jhunjhunu Road", "Ratanpura", "Gandhi Chowk", "Sikar Fort", "Chandpole", "Sikandara", "Shivpuri", "Seth Ji Ki Haveli", "Rani Sati Dadi Temple", "Sadar Bazar", "Agarwal Market"),
                "Jhunjhunu" to listOf("Jhunjhunu Fort", "Rani Sati Temple", "Sikar Road", "Rajpur", "Mandawa", "Nawalgarh", "Chirawa", "Udaipurwati", "Banswa", "Sardarshahar", "Khetri", "Seth Ji Ki Haveli"),
                "Barmer" to listOf("Barmer Fort", "Rani Bhatiyani Temple", "Guda Malani", "Juna Barmer", "Gadi Sagar Lake", "Ramdevra", "Kedwal", "Balotra", "Shiv", "Rajiv Gandhi Nagar", "Sadar Bazar", "Bhavgarh")
            ),
            "Gujarat" to mapOf(
                "Ahmedabad" to listOf("Ellisbridge", "Navrangpura", "Maninagar", "Vastrapur", "Ghatlodia", "Satellite", "Ashram Road", "CG Road", "Naranpura", "Bopal", "Prahlad Nagar", "Sarkhej", "Kankaria", "Sola", "Shahibaug"),
                "Surat" to listOf("Adajan", "Varachha", "Palanpur", "Sarthana", "Gopi Talav", "Udhna", "Rander", "Vesu", "Katargam", "Surat City", "Kavi Narmad Central Library Area", "Akhroli", "Ghod Dod Road", "Sukhadia", "Agarwal Mill"),
                "Vadodara" to listOf("Alkapuri", "Sayajigunj", "Wadi", "Karelibaug", "Narmada Canal Road", "Vishwamitri", "Ajwa Road", "GEB Colony", "Subhanpura", "Panchvati", "Gandhinagar", "Gotri", "Manjalpur", "Khanderao Market", "Old Padra Road"),
                "Rajkot" to listOf("Ashram Road", "Race Course", "Gondal Road", "Kalavad Road", "Jagnath", "Sardar Nagar", "Mota Mava", "Kothariya", "Vidhya Nagar", "Shapar", "Khanderao Market", "Jawala", "Rajendranagar", "Narmada Canal Road"),
                "Gandhinagar" to listOf("Sector 21", "Sector 16", "Sector 9", "Sector 7", "Sector 1", "Sector 30", "Sector 5", "Sector 11", "Sector 8", "Sector 29", "Sector 6", "Sector 4", "Sector 12", "Sector 23", "Sector 20"),
                "Bhavnagar" to listOf("Vidyut Nagar", "Waghawadi Road", "Sardar Patel Colony", "Gopnath", "Kalanala", "Madhav Nagar", "Sardar Nagar", "Gokul Nagar", "Khodiyar Colony", "Sukhpar", "Prabhat Colony", "Palitana", "New Civil Hospital Area", "Gandhi Road"),
                "Jamnagar" to listOf("Kutchhery Road", "Dhrol", "Pancheshwar", "Gujarat Housing Board", "Bedi Bunder", "Jamwadi", "Shiv Nagar", "Gopalpura", "Madhav Nagar", "Rajkot Road", "GIDC", "Madhuranagar", "Ranjit Sagar", "Shankarpur"),
                "Vapi" to listOf("Vapi City", "Silvassa Road", "GIDC", "Madhuban", "Amli", "Khadi", "Kharadpada", "Nani Daman", "Tadkeshwar", "Daman", "Vapi-South", "Dharmshala Road", "Bharuch Road", "Chala", "Naroli"),
                "Mehsana" to listOf("GIDC", "Sardar Patel Park", "Kadi", "Unjha", "Mahesana City", "Patan", "Vijapur", "Kheralu", "Siddhpur", "Tharad", "Kangashia", "Talod", "Patan Bypass", "Narmada Canal Road", "Saraswati Nagar"),
                "Surendranagar" to listOf("Surendranagar City", "Muli", "GIDC", "Rajkot Road", "Dhrangadhra", "Gujarat Housing Board", "Dhandhuka", "Lakhtar", "Limbdi", "Chotila", "Upleta", "Khambhalia", "Chandisar", "Vadhwan", "Dhasa"),
                "Dholera" to listOf("Dholera City", "Dholera Special Investment Region", "Dholera Village", "Gadhshisa", "Ranchhodpura", "Vejalpur", "Khodiyar", "Dholera Industrial Area", "Dholera Smart City", "Gujar Khan", "Bhankhar"),
                "Kutch" to listOf("Bhuj", "Kandla", "Mandvi", "Anjar", "Gandhidham", "Nakhatrana", "Rapar", "Mundra", "Lakhpat", "Dholavira", "Patan", "Vijayapara", "Rapar", "Kutchhary", "Lakhpat", "Kachchh")
            ),
            "Madhya Pradesh" to mapOf(
                "Bhopal" to listOf("BHEL", "Arera Colony", "Maharana Pratap Nagar", "MP Nagar", "Kolar Road", "Old City", "New Market", "Shivaji Nagar", "Habibganj", "Rajabada", "Chuna Bhatti", "T T Nagar", "Jawahar Chowk", "Ganga Jamuna"),
                "Indore" to listOf("Rajwada", "Malharganj", "Lal Baag", "Pipliyahana", "Vijay Nagar", "Chhawni", "Kalimata", "Narmada Road", "Rau", "Annapurna", "Bombay Hospital Area", "Khajrana", "Tejaji Nagar", "A B Road", "M G Road"),
                "Jabalpur" to listOf("Madan Mahal", "Dawa Bazar", "Napier Town", "Gundarmao", "Ranjhi", "Shivaji Nagar", "Sadar", "Civil Lines", "New Life", "Panchwati", "Karmeta", "Jabalpur Cantt", "Kachnar City", "Chappan", "Sadar Bazar"),
                "Gwalior" to listOf("Gwalior Fort Area", "Lashkar", "City Center", "Gola Ka Mandir", "Mahalgaon", "Padav", "New Rajendra Nagar", "Sukh Sagar", "Gandhi Road", "Indergarh", "Kailash Vihar", "Gajraula", "Pipari", "Naya Gaon"),
                "Ujjain" to listOf("Mahakaleshwar Temple Area", "Chappal Market", "Freeganj", "Vikramaditya Marg", "Ujjain City", "Makhanlal Chaturvedi", "Kothi Road", "Bhopal Road", "Shivaji Nagar", "Chandan Nagar", "Shivpuri", "Mangal Nagar", "Saraswati Nagar", "Madhav Nagar"),
                "Sagar" to listOf("Naya Ganj", "T T Nagar", "Civil Lines", "Shiv Nagar", "Sadar", "Sagar Cantt", "Sagar City", "Bhilai", "Madhav Nagar", "Gadarwara", "Vikramshila", "Ranjhi", "Kachnar City", "Mau", "Rajapetha"),
                "Satna" to listOf("Satna City", "Mau", "New Market", "Jwala Estate", "Sangram", "Pali", "Sadar", "Sanjay Gandhi Nagar", "Birla Vihar", "Amraiwadi", "Satna Cantt", "Mau Road", "Ramnagar", "Kachari", "Krishna Nagar"),
                "Khandwa" to listOf("Khandwa City", "Satya Sai Nagar", "Jain Mandir", "Bhagwanpura", "Gali", "Mahadev Nagar", "Kachnar City", "Ramayana", "New Khandwa", "Sadar", "Narmada Nagar", "Kachheri", "Shivaji Nagar", "Kusum Nagar", "Basantpura"),
                "Rewa" to listOf("Rewa City", "Gole Bazar", "Sanjay Gandhi Nagar", "Mau", "Pali", "Rewa Road", "Nirmal Nagar", "Satna Road", "Sadar", "Rewa Cantt", "Sanjay Colony", "Birla Colony", "Mau Road", "Kachhari", "Shivpuri"),
                "Hoshangabad" to listOf("Hoshangabad City", "Hoshangabad Cantt", "Bairagarh", "Hoshangabad Road", "Satpura Nagar", "Sadar", "Kachnar City", "Ravi Nagar", "Shivaji Nagar", "Chandrapur", "Ranjhi", "New Hoshangabad", "Patan", "Chandrapur"),
                "Chhindwara" to listOf("Chhindwara City", "Pachmarhi", "Sadar", "Chhindwara Road", "Shivaji Nagar", "Vikramshila", "New Market", "Mau", "Kachhari", "Raja Chowk", "Ravi Nagar", "Baba Nagar", "Shivpuri", "Madhav Nagar")
            ),
            "Tamil Nadu" to mapOf(
                "Chennai" to listOf("Adyar", "T Nagar", "Besant Nagar", "Mylapore", "Nungambakkam", "Velachery", "Anna Nagar", "Pallikaranai", "Ekkatuthangal", "Alwarpet", "Sholinganallur", "Guindy", "Madhavaram", "Tiruvanmiyur", "Kotturpuram"),
                "Coimbatore" to listOf("RS Puram", "Gopalapuram", "Peelamedu", "Saibaba Colony", "Tidel Park", "Singanallur", "Ganapathy", "Vadavalli", "Hope College", "Town Hall", "Civil Aerodrome", "Ukkadam", "Podanur", "Kuniamuthur", "Rathinapuri"),
                "Madurai" to listOf("Meenakshi Temple Area", "KK Nagar", "MGR Nagar", "Tirumangalam", "Arappalayam", "Chinna Chokkikulam", "Vadipatti", "Nagamani", "Simmakkal", "Mariappan", "Koodal Nagar", "Vilangudi", "Thirunagar", "Sholavandan", "Tallakulam"),
                "Tiruchirappalli" to listOf("Srirangam", "Maharaja Complex", "Thillai Nagar", "K K Nagar", "Ponmalai", "Puthur", "Vayalur", "Samayapuram", "Cantonment", "Tidel Park", "Sowripalayam", "Srirangam", "Golden Rock", "Sembanarkovil", "Puthur"),
                "Salem" to listOf("Omalur", "Sankari", "Attur", "Salem City", "Hasthampatti", "Mecheri", "Salem Junction", "Suramangalam", "Kitchipalayam", "Saidapet", "Boys Town", "Kottai", "Shevapet", "New Bus Stand", "Rajiv Gandhi Nagar"),
                "Trichy" to listOf("Srirangam", "K K Nagar", "Thillai Nagar", "Maharaja Complex", "Puthur", "Golden Rock", "Cantonment", "Ponmalai", "Samayapuram", "Vayalur", "Sembanarkovil", "Sowripalayam", "Gandhi Market", "Trichy Town", "Srirangam"),
                "Tirunelveli" to listOf("Palayamkottai", "Tirunelveli Town", "Nellai", "Sankarankovil", "Tiruchendur", "Nallur", "Vallioor", "Kunnathur", "Muthuramalingam", "Meenakshipuram", "Rajapalayam", "Gandhinagar", "Naduvakkarai", "Karivalamvandanallur", "Vallioor"),
                "Vellore" to listOf("Vellore City", "Katpadi", "Sathuvachari", "Bagayam", "Periya Pudur", "Kavalur", "Rathinapuri", "Nethaji Nagar", "Athur", "Kuppam", "Suthanthira Nager", "Nehru Nagar", "Palavansathu", "Kanchipuram", "Vaniyambadi"),
                "Erode" to listOf("Erode City", "Brough Road", "Perundurai", "Sengodampalayam", "Thindal", "Kasipalayam", "Bhavani", "Vellode", "Kongu Nagar", "Tharapuram", "Gobi", "Kodumudi", "Pallipalayam", "Moolanur", "Mettur"),
                "Kanchipuram" to listOf("Kanchipuram City", "Gingee", "Sriperumbudur", "Periya Sembakkam", "Maraimalai Nagar", "Thirukalukundram", "Chengalpattu", "Nandivaram", "Kattankulathur", "Pallavaram", "Acharapakkam", "Puthur", "Vandalur", "Nellikuppam", "Mambakkam"),
                "Nagapattinam" to listOf("Nagapattinam City", "Sirkali", "Thiruthuraipoondi", "Kilvelur", "Vedaranyam", "Kizhakevayal", "Nannilam", "Pattamangalam", "Pudukottai", "Kottur", "Tiruvarur", "Vedaranyam", "Kariamanickam", "Kollidam", "Muthupet")
            ),
            "Haryana" to mapOf(
                "Gurugram" to listOf("DLF Phase 1", "DLF Phase 2", "DLF Phase 3", "Cyber City", "MG Road", "Sushant Lok", "Udyog Vihar", "Sohna Road", "Sector 14", "Sector 15", "Sector 56", "Golf Course Road", "South City", "Palam Vihar", "Rajendra Park"),
                "Faridabad" to listOf("Sector 15", "Sector 16", "Sector 17", "Sector 21", "Sector 28", "Sector 37", "Sector 46", "Sector 55", "Sector 62", "Old Faridabad", "Badkhal", "NIT", "Ballabgarh", "Tigaon", "Neelam Bata Road"),
                "Panipat" to listOf("Sector 13", "Sector 25", "Sector 29", "Model Town", "Civil Lines", "Kunjpura Road", "Huda Sector 1", "Huda Sector 2", "Huda Sector 3", "Karnal Road", "Jattal Road", "Panipat City", "Panipat Industrial Area", "Dewan Khana", "Marron"),
                "Ambala" to listOf("Ambala Cantt", "Ambala City", "Mandi Ambala", "Sector 9", "Sector 11", "Sector 12", "Sector 16", "Sector 17", "Rattan Mohalla", "Gali No 1", "Gali No 2", "Old Ambala", "Jind Road", "Kurukshetra Road", "Vikram Vihar"),
                "Hisar" to listOf("Sector 15", "Sector 16", "Sector 17", "Sector 18", "Sector 20", "Sector 21", "Sector 22", "Sector 23", "Sector 24", "Sector 25", "Civil Lines", "Bhagat Singh Colony", "Nehru Ground", "Rajiv Gandhi Park", "Old Hisar"),
                "Rohtak" to listOf("Sector 1", "Sector 2", "Sector 3", "Sector 4", "Sector 5", "Sector 6", "Sector 7", "Sector 8", "Sector 9", "Sector 10", "Civil Lines", "Model Town", "Maharaja Surajmal Road", "Rohilla", "Dabri"),
                "Karnal" to listOf("Sector 12", "Sector 13", "Sector 14", "Sector 15", "Sector 32", "Karnal City", "Panipat Road", "G.T. Road", "Basant Vihar", "Nirmal Vihar", "Karan Vihar", "Prithvi Raj Road", "Kamla Nehru Park", "Pallikaranai", "Madhuban"),
                "Sonipat" to listOf("Sector 1", "Sector 2", "Sector 3", "Sector 4", "Sector 5", "Sector 6", "Sector 7", "Sector 8", "Sector 9", "Sector 10", "Ganaur", "Rohini", "Baroda", "Haryana Vidyapeeth", "Narela Road"),
                "Yamunanagar" to listOf("Sector 17", "Sector 18", "Sector 19", "Sector 20", "Civil Lines", "Vikas Nagar", "Jagdish Colony", "Huda Sector 1", "Huda Sector 2", "Huda Sector 3", "G.T. Road", "Madhuban", "Sanjay Colony", "Taraori", "Radaur"),
                "Panchkula" to listOf("Sector 1", "Sector 2", "Sector 3", "Sector 4", "Sector 5", "Sector 6", "Sector 7", "Sector 8", "Sector 9", "Sector 10", "Pinjore", "Mansa Devi", "Kalka", "Huda Sector 1", "Huda Sector 2"),
                "Jhajjar" to listOf("Jhajjar City", "Sector 1", "Sector 2", "Sector 3", "Civil Lines", "Kheri Road", "Dharuhera", "Mundka", "Haryana Industrial Estate", "Bhiwani Road", "Chaudhary Charan Singh Road", "Mali Khera", "Jharoda", "Narela Road", "Bapora")
            ),
            "Telangana" to mapOf(
                "Hyderabad" to listOf("Banjara Hills", "Jubilee Hills", "Hitech City", "Gachibowli", "Secunderabad", "Kondapur", "Shamshabad", "Madhapur", "Begumpet", "Old City", "Abids", "Borabanda", "Mehdipatnam", "Kukatpally", "Himayatnagar"),
                "Warangal" to listOf("Hanamkonda", "Kazipet", "Warangal City", "Jangaon", "Narsampet", "Raghunathapally", "Parkal", "Pochampally", "Wazeedu", "Mahabubabad", "Tadwai", "Ghanpur", "Siddipet", "Nekkonda", "Raiparthy", "Thorrur"),
                "Khammam" to listOf("Khammam City", "Wyra", "Sattupalli", "Palair", "Bhadrachalam", "Vemsoor", "Aswaraopet", "Jangareddygudem", "Raghunathapalem", "Chintakani", "Kallur", "Kusumanchi", "Rayanapadu", "Gopalasamudram", "Madhira"),
                "Nizamabad" to listOf("Nizamabad City", "Armoor", "Bodhan", "Kammarpalli", "Dichpally", "Yellareddy", "Nandipet", "Koratla", "Jagtial", "Jagtial Road", "Kuntala", "Rajanna Sircilla", "Morthad", "Kamareddy", "Bheemgal"),
                "Karimnagar" to listOf("Karimnagar City", "Huzurabad", "Vemulawada", "Ramagundam", "Jammikunta", "Manthani", "Peddapalli", "Choppadandi", "Sircilla", "Kothapet", "Sangareddy", "Rudrangi", "Koratla", "Vemulawada", "Rajanna Sircilla"),
                "Medak" to listOf("Medak City", "Sangareddy", "Narsapur", "Dubbak", "Gajwel", "Zaheerabad", "Peddaganjam", "Mandal", "Toopran", "Masaipet", "Nagireddypet", "Raikode", "Siddipet", "Peddapalli", "Kondapaka"),
                "Adilabad" to listOf("Adilabad City", "Kagaznagar", "Mancherial", "Bellampalli", "Nirmal", "Tadgaon", "Kotapalli", "Kannepalli", "Lingapur", "Bhainsa", "Naspur", "Talamadugu", "Kallur", "Waddepally", "Kagaznagar"),
                "Jagtiyal" to listOf("Jagtiyal City", "Jagtial", "Vemulawada", "Kondagattu", "Dharmapuri", "Metpalli", "Kamalapur", "Nellipaka", "Mothkur", "Jainoor", "Bichkunda", "Raikal", "Peddapalli", "Kothapalli", "Thimmapur"),
                "Nalgonda" to listOf("Nalgonda City", "Nalgonda Town", "Miryalaguda", "Peddaganjam", "Suryapet", "Choutuppal", "Devarakonda", "Nallabelli", "Chityal", "Damaracherla", "Pochampally", "Sathupally", "Narakoduru", "Huzurnagar", "Macherla"),
                "Rangareddy" to listOf("Rangareddy District", "Miyapur", "Uppal", "LB Nagar", "Madhapur", "Gachibowli", "Kukatpally", "Mehdipatnam", "Shamshabad", "Secunderabad", "Nagole", "Santoshnagar", "Narsingi", "Rajendranagar", "Serilingampally")
            ),
            "Kerala" to mapOf(
                "Thiruvananthapuram" to listOf("Palayam", "Kowdiar", "Vellayambalam", "Karamana", "Venpalavattom", "Sreekariyam", "Technopark", "Nanthancode", "Pettah", "Kazhakkoottam", "Sreekariyam", "Kazhakoottam", "Balaramapuram", "Attingal", "Parassala"),
                "Kochi" to listOf("Ernakulam", "MG Road", "Kakkanad", "Vyttila", "Kaloor", "Fort Kochi", "Mattancherry", "Aluva", "Edappally", "Palarivattom", "Cheranelloor", "Puthenpalli", "Thykoodam", "South End", "North End"),
                "Kozhikode" to listOf("City Centre", "Mavoor Road", "Calicut Beach", "Kallai", "Pavamani Road", "West Hill", "Indira Gandhi Road", "Kozhikode Town", "Nellikkode", "Puthiyara", "Mananchira", "Puthiyangadi", "Meenchanda", "Nadakkavu", "Kuttikkattur"),
                "Thrissur" to listOf("Thrissur City", "Shornur Road", "Punnamada", "Wadakkanchery", "Kottappuram", "Kuriakose Nagar", "Irinjalakuda", "Chalakudy", "Nellayi", "Cheroor", "Ollur", "Peringavu", "Mullakkal", "Punnamoodu", "Palluruthy"),
                "Kottayam" to listOf("Kottayam Town", "Changanassery", "Puthuppally", "Kumarakom", "Mutholy", "Meenachipuram", "Kottayam City", "Kottayam Railway Station", "Sreekrishnapuram", "Puthenpally", "Sivagiri", "Mutholy", "Cherpunkal", "Manarcad", "Kottayam East"),
                "Palakkad" to listOf("Palakkad City", "Chittur", "Pudussery", "Koduvayur", "Muthur", "Sreekrishnapuram", "Mannarkkad", "Nelliyampathy", "Kanjikode", "Olavakkode", "Rathinasabha", "Puthur", "Chalakudy", "Ramanathapuram", "Palakkad Town"),
                "Alappuzha" to listOf("Alappuzha Town", "Punnamada", "Kuttanadu", "Cherthala", "Mannanchery", "Kuttanadu", "Mundakkal", "Punnappra", "Changanassery", "Karappuram", "Chennamkary", "Nedumudi", "Sreekrishnapuram", "Mannanchery", "Karuvatta"),
                "Kannur" to listOf("Kannur City", "Thalassery", "Payyannur", "Sreekandapuram", "Pappinisseri", "Pallikunnu", "Kannur Railway Station", "Iritty", "Nadapuram", "Sreekandapuram", "Koothuparamba", "Vengad", "Kadirur", "Muzhappilangad", "Kannur Town"),
                "Wayanad" to listOf("Kalpetta", "Sultan Bathery", "Mananthavady", "Meppadi", "Vythiri", "Ambalavayal", "Pulpally", "Sreekandapuram", "Cherambadi", "Panamaram", "Nelliyambam", "Edakkal", "Muttil", "Meppadi", "Wayanad Town"),
                "Pathanamthitta" to listOf("Pathanamthitta Town", "Sabarimala", "Thiruvalla", "Chengannur", "Mannadi", "Pallikkathodu", "Puthoor", "Ranni", "Konni", "Elanthur", "Kozhencherry", "Kozhencherry", "Adoor", "Muthoor", "Mallappally")
            ),
            "Karnataka" to mapOf(
                "Bengaluru" to listOf("Koramangala", "Indiranagar", "MG Road", "Whitefield", "Electronic City", "Jayanagar", "Hosur Road", "Banaswadi", "Hebbal", "Ulsoor", "Rajajinagar", "BTM Layout", "Kalyan Nagar", "Yeshwanthpur", "Bellandur"),
                "Mysuru" to listOf("Devaraja Mohalla", "V.V. Puram", "Jayalakshmipuram", "Nazarbad", "Kuvempunagar", "Metagalli", "Hunsur Road", "Ravindranath Tagore Road", "Siddhartha Layout", "Dattagalli", "Bogadi", "Bannimantap", "Varuna", "Gokulam"),
                "Hubballi-Dharwad" to listOf("Keshwapur", "Gokul Road", "Hubli City", "Dharwad", "Unkal", "Kumar Garden", "Railway Layout", "Navanagar", "Vidyanagar", "Shivajinagar", "Dajibanpeth", "Banashankari", "Sattur", "Kittur Chennamma Circle"),
                "Mangalore" to listOf("Panjimogaru", "Somashekhar", "Bejai", "Kadri", "Kankanady", "Bunts Hostel", "Light House Hill", "Hampankatta", "Jeppu", "Gokarnanatheshwara", "Kulshekar", "Shakti Nagar", "Kottara", "Mary Hill", "Attavar"),
                "Belagavi" to listOf("Rani Chennamma Circle", "Khanapur Road", "Shivaji Nagar", "Ganeshpur", "Talim", "Belagavi City", "Vantmuri", "Kalasapet", "Vaddarmatti", "Jamboti", "Nehru Nagar", "Khade Bazaar", "Sangolli Rayanna Circle", "Rugmani Konda"),
                "Bidar" to listOf("Bidar City", "Khanapur", "Maniknagar", "Ashtur", "Nadargi", "Nagapur", "Gulbarga Road", "Kumbhi", "Bidar Fort", "Babanpur", "Chittapur", "Karadagi", "Narsapur", "Yadgir", "Jambagi"),
                "Gulbarga" to listOf("Gulbarga City", "Khanapur", "Kamalapur", "Siddipet", "Alapur", "Haliyala", "Rajapur", "Gulbarga Fort", "Swami Samarth Nagar", "Srinivas Colony", "Yadgir", "Chincholi", "Yadgir Road", "Husainabad", "Chetan Sagar"),
                "Tumakuru" to listOf("Tumakuru City", "Doddaballapura", "Gubbi", "Sira", "Pavagada", "Tumkur Road", "Madhugiri", "Gundlupet", "Chikkanayakanahalli", "Koratagere", "Tiptur", "Haranahalli", "Vittalapura", "Kerehalli", "Korasavadi"),
                "Udupi" to listOf("Udupi City", "Manipal", "Kundapura", "Karkala", "Chikkamagaluru", "Santekatte", "Byndoor", "Hebri", "Hosanagara", "Shirva", "Kapu", "Mudarangadi", "Moodubidri", "Yellur", "Vamanjoor"),
                "Shimoga" to listOf("Shimoga City", "Bhadravathi", "Sagar", "Shikaripur", "Hosanagara", "Thirthahalli", "Koppa", "Siddapura", "Hosanagara", "Kadur", "Kargal", "Narasimharajapura", "Shimoga Rural", "Adivasi Colony", "Kundadri"),
                "Chikmagalur" to listOf("Chikmagalur City", "Mudigere", "Koppa", "Aldur", "Sakleshpur", "Javagal", "Narasimharajapura", "Belur", "Kalasa", "Lakya", "Balehonnur", "Sakleshpur", "Gundya", "Kadur", "Karkala")
            ),
            "West Bengal" to mapOf(
                "Kolkata" to listOf("Ballygunge", "Salt Lake City", "Park Street", "Howrah", "Alambazar", "New Market", "Behala", "Garia", "Dum Dum", "Jadavpur", "Tollygunge", "Kalighat", "Esplanade", "Bidhannagar", "Rajarhat"),
                "Howrah" to listOf("Howrah City", "Belur", "Salkia", "Uttarayon", "Shibpur", "Liluah", "Daspur", "Jagachha", "Dhulagarh", "Bally", "Dasnagar", "Kolkata Road", "West Howrah", "Mominpur", "Rishra"),
                "Durgapur" to listOf("Durgapur City", "Bidhan Nagar", "Bokaro", "Jamuria", "Asansol", "Panagarh", "Amojora", "Durgapur Steel Town", "Kanksa", "Paharudi", "Durgapur Rishra", "Rajbandh", "Khopra", "Burdwan", "Chandanpur"),
                "Siliguri" to listOf("Siliguri City", "Hakimpara", "Pradhan Nagar", "Mahananda", "Salugara", "Bidhan Market", "Rishra", "Siliguri Junction", "Jorpokhri", "Jalpaiguri", "Kalimpong", "Soureni", "Matigara", "Raghunathganj", "Bagdogra"),
                "Jalpaiguri" to listOf("Jalpaiguri City", "Rajganj", "Nagrakata", "Maynaguri", "Dhupguri", "Malbazar", "Siliguri", "Ranishwar", "Dhumdum", "Mongalkote", "Jalpaiguri Town", "Raghunathganj", "Chengmari", "Bhutan Border"),
                "Asansol" to listOf("Asansol City", "Durgapur", "Kulti", "Burnpur", "Raniganj", "Burdwan", "Sonapur", "Gaurangdih", "Amdanga", "Galsi", "Hirapur", "Jadugora", "Barabani", "Banki", "Jamuria"),
                "Burdwan" to listOf("Burdwan City", "Purba Bardhaman", "Katwa", "Durgapur", "Bardhaman", "Ranishwar", "Burdwan Town", "Katwa", "Mongalkote", "Guskara", "Khandaghosh", "Memari", "Purbasthali", "Banshberia", "Panchla"),
                "Kharagpur" to listOf("Kharagpur City", "Hijli", "Kharagpur Junction", "Mogra", "Bajkul", "Kharagpur Town", "Salboni", "Lalgarh", "Jitpur", "Haldia", "Sikharpur", "Pingla", "Gokulnagar", "Madhupur", "Kanthi"),
                "Midnapore" to listOf("Midnapore City", "Jhargram", "Kharagpur", "Belda", "Egra", "Keshpur", "Salboni", "Dantan", "Madhupur", "Daspur", "Debra", "Panskura", "Mogra", "Gokulnagar", "Narayangarh"),
                "Malda" to listOf("Malda City", "English Bazar", "Kaliachak", "Chanchal", "Manikchak", "Mothabari", "Bamangola", "Harishchandrapur", "Old Malda", "Gajol", "Ratua", "Pandua", "Kaliachak Town", "Nabagram", "Harishchandrapur")
            ),
            "Uttarakhand" to mapOf(
                "Dehradun" to listOf("Paltan Bazaar", "Rajpur Road", "Chukkuwala", "Vikasnagar", "Clement Town", "Mussoorie Road", "Kashmiribagh", "Nehru Colony", "Dalanwala", "Bhagwantpur", "Saharanpur Road", "Karanpur", "Haridwar Road", "Saddatganj", "Premnagar"),
                "Haridwar" to listOf("Har Ki Pauri", "Bairagi Camp", "Jwalapur", "Kankhal", "Mangal Pandey Nagar", "Shivalik Nagar", "Bhupatwala", "Ranipur", "Siddheshwar", "Lalita Puram", "Narsan", "Kishanpur", "Roshnabad", "Jawalapur", "Shivaji Nagar"),
                "Rishikesh" to listOf("Lakshman Jhula", "Ram Jhula", "Badrinath Road", "Muni Ki Reti", "Swarg Ashram", "Tapovan", "Rishikesh City", "Haridwar Road", "Phool Chatti", "Paliwal Market", "Bhagwati Vihar", "Ganga Nagar", "Raipur", "Badrinath Road", "Sangam Vihar"),
                "Nainital" to listOf("Mall Road", "Tallital", "Bright End Corner", "Raj Bhawan", "Bhowali", "Sukhatal", "Gorakhpur", "Panchgani", "Ayarpatta", "Haldwani Road", "Naina Devi Temple Area", "Naina Peak", "Pangot", "Khurpatal", "Haldwani"),
                "Haldwani" to listOf("Haldwani City", "Lalpur", "Kichha", "Rohilkhand", "Maheshpur", "Rampur Road", "Banbhoolpura", "Kathgodam", "Paltan Bazaar", "Haldwani Road", "Patiyali", "Hira Nagar", "Chorgalia", "Bilhaur", "Jankichuli"),
                "Rudrapur" to listOf("Rudrapur City", "Rudrapur Road", "Kichha", "Shivajinagar", "Rohilkhand", "Civil Lines", "Danda", "Ranjitpura", "Ghorakhal", "Maheshpur", "Rajpura", "Harsoli", "Modi Nagar", "Saini Nagar", "Shyam Nagar"),
                "Udham Singh Nagar" to listOf("Udham Singh Nagar City", "Kashipur", "Jaspur", "Rudrapur", "Kichha", "Rudrapur Road", "Maheshpur", "Sidcul", "Shivpuri", "Pantnagar", "Gola", "Nainital Road", "Brijpur", "Nagla", "Rohilkhand"),
                "Almora" to listOf("Almora City", "Lalpchand", "Ranikhet", "Kashipur", "Kasardevi", "Dwarahat", "Bageshwar", "Syalde", "Majkhali", "Tawaghat", "Kosi", "Pancheshwar", "Berinag", "Harkot", "Ranmala"),
                "Chamoli" to listOf("Chamoli City", "Joshimath", "Badrinath", "Mana", "Govindghat", "Nandaprayag", "Chamoli Ghat", "Gopeshwar", "Tharali", "Kanol", "Madhyamaheshwar", "Pandukeshwar", "Bhattwari", "Helang", "Pallia"),
                "Pauri Garhwal" to listOf("Pauri City", "Kotdwar", "Dugadda", "Lansdowne", "Rikhnikhal", "Bhairopur", "Khirsu", "Jaspur", "Nainidanda", "Srinagar", "Thalisain", "Kandai", "Bhatwari", "Nagnath", "Paundh"),
                "Tehri Garhwal" to listOf("Tehri City", "New Tehri", "Bhagirthipur", "Kandari", "Chamba", "Devprayag", "Koteshwar", "Nagni", "Kirtinagar", "Beri", "Kothari", "Mori", "Bhangeli", "Kirtinagar", "Mohi"),
                "Bageshwar" to listOf("Bageshwar City", "Kanda", "Kausani", "Someshwar", "Gairar", "Berinag", "Dwarahat", "Chaukori", "Pindar", "Kalat", "Bagwan", "Jamauli", "Jwalapuri", "Nagni", "Gangolihaat", "Kheti"),
                "Ranikhet" to listOf("Ranikhet City", "Panchkula", "Lalkuan", "Bhatia", "Haldwani", "Majkhali", "Kalimath", "Syalde", "Kuchkuch", "Kaundiny", "Kacheri", "Pindari", "Chaubattia", "Shivpuri", "Kashipur")
            ),
            "Himachal Pradesh" to mapOf(
                "Shimla" to listOf("Mall Road", "Lakkar Bazaar", "The Ridge", "Chhota Shimla", "Sanjauli", "Jakhoo", "Vikasnagar", "Kalka Shimla Highway", "Boileauganj", "Sanjauli", "Tutikandi", "Sector 5", "Sector 6"),
                "Manali" to listOf("Mall Road", "Old Manali", "Hadimba Temple Area", "Solang Valley", "Manu Temple", "Vashisht", "Naggar", "Aleo", "Patlikuhl", "Sultanpur", "Kothi", "Rohtang Pass", "Keylong"),
                "Dharamshala" to listOf("McLeod Ganj", "Dharamkot", "Kangra", "Khaniyara", "Naddi", "Bharmaur", "Trekking Areas", "Sidhbari", "Palampur", "Jiya", "Triund", "Bhagsu", "Dal Lake"),
                "Kullu" to listOf("Kullu Town", "Manikaran", "Naggar", "Raison", "Bhuntar", "Kasol", "Sultanpur", "Jari", "Pothia", "Sarai", "Garsa", "Ani", "Banjar"),
                "Solan" to listOf("Solan City", "Kasauli", "Chail", "Pinjore", "Baddi", "Parwanoo", "Barog", "Rajgarh", "Salogra", "Dagshai", "Bharari", "Shogi", "Sadhora"),
                "Bilaspur" to listOf("Bilaspur Town", "Sadar", "Ghumarwin", "Jhandutta", "Kangra", "Darlaghat", "Bharari", "Sundernagar", "Naina Devi", "Swadi", "Pachhad", "Bhulath", "Kotkhai"),
                "Hamirpur" to listOf("Hamirpur Town", "Nadaun", "Bhoranj", "Jhaniara", "Tira Sujanpur", "Sujanpur", "Kangra", "Bangalore", "Maharana", "Bharari", "Mandi", "Neri", "Nadaun"),
                "Chamba" to listOf("Chamba Town", "Dalhousie", "Khajjiar", "Bharmour", "Pangi", "Sihunta", "Tissa", "Churah", "Dharamshala", "Jaswan", "Killar", "Bharmaur", "Nainikhad"),
                "Kangra" to listOf("Kangra Town", "Palampur", "Dharamshala", "Jwalaji", "Kangra Fort", "Nagrota", "Karsog", "Baijnath", "Sujanpur", "Gaggal", "Chintpurni", "Ghumarwin", "Bharari"),
                "Una" to listOf("Una Town", "Amb", "Haroli", "Bangana", "Mehatpur", "Nangal", "Gagret", "Dina", "Panjehra", "Barsar", "Rohru", "Nari", "Bharari")
            ),
            "Punjab" to mapOf(
                "Chandigarh" to listOf("Sector 17", "Sector 22", "Sector 34", "Sector 35", "Sector 43", "Sector 45", "Sector 49", "Sector 56", "Sector 62", "Sector 70", "Sector 88", "Sector 40", "Sector 23"),
                "Amritsar" to listOf("Golden Temple Area", "Jallianwala Bagh", "Raja Sansi", "Attari", "Beas", "Hall Gate", "Khalra", "Shastri Market", "Guru Ka Mahal", "Mall Road", "Lawrence Road", "Vikram Singh Nagar", "Ranjit Avenue"),
                "Ludhiana" to listOf("Ferozepur Road", "Ghumar Mandi", "Model Town", "Clock Tower", "Raman", "Jodhewal", "Gill Road", "Pakhowal Road", "Dugri", "Kangniwal", "Kitchlu Nagar", "Sarabha Nagar", "Keshornagar"),
                "Jalandhar" to listOf("Loharan", "Model Town", "Ranjit Avenue", "Cantt Area", "Bhagat Singh Chowk", "Danda Village", "Raja Sansi", "Kapurthala Road", "Choudhary", "Lajpat Nagar", "Sainik Colony", "Chheharta", "Mangal Pandey Nagar"),
                "Patiala" to listOf("Qila Mubarak", "Rajpura", "Leela Bhawan", "Baradari", "Gurdev Nagar", "New Mandi", "Patiala Cantt", "Sirhind", "Mall Road", "Vikram Singh Nagar", "Sadar Bazar", "Raja Alipur", "Bhupindra Road"),
                "Bathinda" to listOf("Cattle Market", "Railway Road", "Old City", "New Mandi", "Guru Nanak Nagar", "Shiv Nagar", "Mandi", "Mall Road", "Dakha", "Civil Lines", "Harmandir Sahib", "Bibi Wala Road", "Bazar Bhadson"),
                "Mohali" to listOf("Phase 1", "Phase 2", "Phase 3", "Phase 4", "Phase 5", "Sector 66", "Sector 70", "Sector 77", "Sector 79", "Sector 81", "Sector 82", "Sector 83", "Sector 84"),
                "Ferozepur" to listOf("Ferozepur Cantt", "Ferozepur City", "Guru Har Sahai", "Zira", "Moga", "Khuian Sarwar", "Sujanpur", "Sadiq", "Talwandi", "Tarn Taran", "Bhikhiwind", "Dharmkot", "Sukhewal"),
                "Pathankot" to listOf("Pathankot City", "Dhangu", "Sadar Bazar", "Kunjwani", "Dinanagar", "Gurdaspur", "Suchetgarh", "Jammu Road", "Kishanpur", "Chakki Bank", "Saddatpur", "Chawinda", "Sanghol"),
                "Gurdaspur" to listOf("Gurdaspur City", "Batala", "Kadian", "Dhariwal", "Madhopur", "Dera Baba Nanak", "Qadian", "Kalanaur", "Sujanpur", "Kahnuwan", "Kangra", "Gurudaspur", "Dhariwal", "Sri Hargobindpur"),
                "Sangrur" to listOf("Sangrur City", "Malerkotla", "Barnala", "Dhuri", "Sunam", "Mansa", "Lehra", "Dhuri", "Sangrur Rural", "Patran", "Jana", "Akalgarh", "Chapar Chiri"),
                "Kapurthala" to listOf("Kapurthala City", "Begowal", "Sultanpur Lodhi", "Jandiala", "Nawabshah", "Patti", "Phagwara", "Khanpur", "Srinagar", "Khera", "Dhab", "Bunga", "Nihal Singh Wala")
            ),
            "Mizoram" to mapOf(
                "Aizawl" to listOf("Lunglei", "Bara Bazar", "Chanmari", "Khatla", "Zarkawt", "Aizawl East", "Aizawl West", "Tanhril", "Vengthlang", "Tlangnuam", "Bungpui", "Durtlang", "Sihphir"),
                "Lunglei" to listOf("Lunglei Town", "Bungtlang", "Tlabung", "Khawbung", "Hnahthial", "Khawzawl", "Siaha", "Rongnung", "Tualpui", "Khawpui", "Ngopa", "Bazarveng", "Vairengte"),
                "Champhai" to listOf("Champhai Town", "Chhingchhip", "Hnahthial", "Tlangsam", "Khuaifang", "Serchhip", "Zawlnuam", "Rih", "Khawthlang", "Ngopa", "Silsury", "Pangzawl"),
                "Serchhip" to listOf("Serchhip Town", "Vangchhia", "Chhuan Thar", "Hengpui", "Saisih", "Senvon", "Ngopa", "Sihphir", "Khawzawl", "Tlabung", "Khawbung", "Farkawn"),
                "Siaha" to listOf("Siaha Town", "Lunglei", "Chhimtuipui", "Khawzawl", "Mamit", "Saiha", "Tualpui", "Zawlnuam", "Hualngo", "Siaha West", "Khawbung", "N. Lungleng"),
                "Mamit" to listOf("Mamit Town", "Rohdih", "Khawthlang", "Tlabung", "Tuanpui", "Mamit Rural", "N. Lungleng", "Hualngo", "Ngopa", "Darlawn", "Lunglei", "Bazarveng"),
                "Kolasib" to listOf("Kolasib Town", "Kolasib", "Vairengte", "Hnathial", "Khawzawl", "Lunglei", "Ngopa", "Bungtlang", "Chhamkhai", "Serchhip", "Tualpui", "Farkawn"),
                "Hnahthial" to listOf("Hnahthial Town", "Chawngtlai", "Siaha", "Khawbung", "Bungtlang", "Zawlnuam", "Tlangnuam", "Hengpui", "Senvon", "Tlabung", "Khawthlang", "Serchhip"),
                "Khawzawl" to listOf("Khawzawl Town", "Siaha", "Mamit", "Serchhip", "Champhai", "Hnahthial", "Lunglei", "Rongnung", "Ngopa", "Khawthlang", "Senvon", "Vairengte"),
                "Darlawn" to listOf("Darlawn Town", "Vairengte", "Mamit", "Khawthlang", "Lunglei", "Bazarveng", "Farkawn", "Senvon", "Ngopa", "Hnahthial", "Serchhip", "Champhai")
            ),
            "Arunachal Pradesh" to mapOf(
                "Itanagar" to listOf("Raj Bhavan", "Ganga", "Naharlagun", "Papum Pare", "Chandranagar", "Karsingsa", "Doimukh", "Holi", "Nirjuli", "Nerwa", "Hawa Camp", "Bank Tinali", "Chimpu", "Nabam Tuki"),
                "Tawang" to listOf("Tawang Town", "Jang", "Bomdila", "Sangetsar Lake", "Madhuri Lake", "Nuranang", "Lhoba", "Lumla", "Gorubathan", "Mongar", "Khinzemane", "Tawang Monastery", "Kangla", "Selapass"),
                "Ziro" to listOf("Ziro Town", "Hong", "Reru", "Yachuli", "Lower Subansiri", "Bamin", "Daporijo", "Daporijo Town", "Sagalee", "Budi", "Yachuli", "Joram", "Takam", "Muriang"),
                "Bomdila" to listOf("Bomdila Town", "Dirang", "Rupa", "Sessa Orchid Sanctuary", "Khaling", "Nameri National Park", "Kalaktang", "Nebo", "Chaygaon", "Bichom", "Yagrung", "Sare", "Lipung"),
                "Pasighat" to listOf("Pasighat Town", "Ruksin", "Mebo", "Yingkiong", "Sille-Oyan", "Namsai", "Mirim", "Boleng", "Lomang", "Pasighat Rural", "Geku", "Diyun", "Gelling", "Rani"),
                "Aalo" to listOf("Aalo Town", "Jirdin", "Gumto", "Pangin", "Liromoba", "Ramang", "Kamba", "Basar", "Kaya", "Geku", "Bameng", "Shamukjong", "Yingkiong", "Bordumsa"),
                "Tirap" to listOf("Tirap Town", "Khonsa", "Deomali", "Longding", "Laju", "Kandong", "Chongkham", "Lazu", "Miao", "Pangsau Pass", "Vijoynagar", "Bhalukpong", "Wakka", "Tirap Rural"),
                "Changlang" to listOf("Changlang Town", "Nampong", "Miao", "Rima", "Kampong", "Lathao", "Manmao", "Raju", "Nampong", "Khamti", "Tirap", "Longding", "Monggu", "Paniduria"),
                "Lower Dibang Valley" to listOf("Roing", "Dambuk", "Mayudia", "Hunli", "Koronu", "Lika", "Mipi", "Borgaon", "Mithun", "Dambuk Town", "Rongli", "Boram", "Oyam", "Mipi"),
                "Upper Siang" to listOf("Pasighat", "Mariyang", "Yingkiong", "Geku", "Riga", "Mirim", "Tingkhat", "Tali", "Kaying", "Sibo-Korong", "Riwot", "Lalung", "Tawang", "Yomcha")
            ),
            "Meghalaya" to mapOf(
                "Shillong" to listOf("Laitumkhrah", "Police Bazaar", "Cleve Colony", "Dhankheti", "Mawlai", "Jhalupara", "Mawpat", "Laban", "Shillong Peak", "Lawsohtun", "Rynjah", "Sadar", "Umiam Lake", "Eidgah"),
                "Tura" to listOf("Chitoktok", "Durama", "Tura Bazar", "Ringrey", "Bungapara", "Nongalbibra", "Nongchram", "Dalu", "Agia", "Tikrikilla", "Bonggre", "Selsella", "Balsora", "Dainadubi"),
                "Jowai" to listOf("Mynkre", "Nongbah", "Puriang", "Nongjri", "Riat Laban", "Khliehriat", "Jowai Town", "Iongkhuli", "Sutnga", "Pynursla", "Nongkhlaw", "Khliehriat", "Tyrna", "Pyndeng"),
                "Williamnagar" to listOf("Williamnagar Town", "Nengmandal", "Mendipathar", "Nengkhra", "Sankol", "Sijong", "Dambuk", "Bongkho", "Mendipathar", "Dambuk", "Ongma", "Pamsang", "Bamkora"),
                "Baghmara" to listOf("Baghmara Town", "Rongara", "Chokpot", "Siju", "Jengjal", "Gomaghat", "Dalu", "Balsora", "Bishnupur", "Bonggre", "Selsella", "Nangalbibra", "Baurik", "Pachy", "Kenduli"),
                "Mairang" to listOf("Mairang Town", "Nonglang", "Khatkasla", "Mawkoh", "Nongkasen", "Mairang West", "Khasi", "Kyrdem", "Rangmaw", "Tongseng", "Nongshyrng", "Kynton", "Tyrna", "Mawthong"),
                "Nongstoin" to listOf("Nongstoin Town", "Mawkyrwat", "Nonglang", "Mawkoh", "Nongblai", "Ranikor", "Nongjri", "Mawshamok", "Nongkynrih", "Nongkhrah", "Lyngkhat", "Nongnah", "Nongpal", "Thangshalai")
            ),
            "Bihar" to mapOf(
                "Patna" to listOf("Patna City", "Boring Road", "Frazer Road", "Kumhrar", "Rajendra Nagar", "Kankarbagh", "Bakhtiyarpur", "Digha", "Gandhi Maidan", "Maidan Garhi", "Pataliputra", "S K Puri", "A N College"),
                "Gaya" to listOf("Gaya Town", "Manpur", "Sherghati", "Bodhgaya", "Kusumgram", "Kacheri", "Khajuri", "Paharpur", "Parsa", "Kumhrar", "Sujanpur", "Badiyahi", "Roh", "Barauli"),
                "Bhagalpur" to listOf("Bhagalpur City", "Sultanganj", "Kahalgaon", "Nathnagar", "Sabour", "Jagdishpur", "Mandar", "Gopalpur", "Ganga River Bank", "Jamalpur", "Pirpainti", "Bihar Sharif", "Kharik"),
                "Muzaffarpur" to listOf("Muzaffarpur Town", "Kanti", "Saraiya", "Gangauli", "Pipra", "Brahmapur", "Bairgania", "Jainagar", "Jamalpur", "Paharpur", "Chhathia", "Katra", "Kharagpur"),
                "Darbhanga" to listOf("Darbhanga City", "Laheria Sarai", "Jale", "Kumarbagh", "Kusheshwar Asthan", "Madhubani", "Sadar", "Shahpur", "Bhagwanpur", "Kanti", "Madhubani", "Samastipur", "Singhwara"),
                "Begusarai" to listOf("Begusarai Town", "Bakhri", "Chautham", "Saharsa", "Tariyani", "Nauhatta", "Dandari", "Jakhoura", "Madhurapur", "Bikramganj", "Munger", "Khagaria", "Gogri"),
                "Arrah" to listOf("Arrah City", "Jagdishpur", "Piro", "Sasaram", "Tarari", "Sathi", "Shahpur", "Buxar", "Charpokhari", "Kochas", "Dumraon", "Dariapur", "Revelganj", "Nawanagar"),
                "Katihar" to listOf("Katihar Town", "Pothia", "Khurhat", "Manihari", "Koliadih", "Mansi", "Aurai", "Dhamdaha", "Dandkhora", "Saraiya", "Bariarpur", "Kumargram", "Pusa"),
                "Samastipur" to listOf("Samastipur City", "Rosera", "Dalsinghsarai", "Kaliari", "Madhubani", "Bhagwanpur", "Chhaurahi", "Sarairanjan", "Karpurigram", "Kusheshwar Asthan", "Hasanpur", "Saraiya"),
                "Sasaram" to listOf("Sasaram City", "Dumraon", "Karpoori Gram", "Chand", "Sasaram Town", "Kochas", "Sheikhpura", "Chhapra", "Saraiya", "Karma", "Dariapur", "Rohua", "Jamui"),
                "Buxar" to listOf("Buxar Town", "Chausa", "Simri", "Dumraon", "Rudrapur", "Sadar", "Sohan", "Bihpur", "Nawada", "Rajpur", "Kachhwara", "Banka", "Lalganj", "Karma"),
                "Munger" to listOf("Munger City", "Jamui", "Saharsa", "Chausa", "Lalganj", "Madhubani", "Bhagalpur", "Buxar", "Kharagpur", "Rajpur", "Barauni", "Sultanganj", "Dharhara")
            ),
            "Jharkhand" to mapOf(
                "Ranchi" to listOf("Ranchi City", "Harmu", "Kanke", "Hatu", "Doranda", "Argora", "Ratu", "Hazaribagh", "Kadamkuan", "Ratu Road", "Main Road", "Upper Bazar", "Bariatu"),
                "Jamshedpur" to listOf("Jamshedpur City", "Bistupur", "Sakchi", "Telco", "Sonari", "Kadma", "Burmamines", "Golmuri", "Gamharia", "Dimna", "Narmada", "Jugsalai", "Adityapur"),
                "Dhanbad" to listOf("Dhanbad City", "Bank More", "Jharia", "Katras", "Sindri", "Koylanagar", "Bharat Coking Coal Limited (BCCL)", "Kumardubi", "Bhuli", "Jorapokhar", "Gandhinagar", "Jharia Township", "Bengal Enamel"),
                "Bokaro" to listOf("Bokaro City", "Sector 4", "Sector 5", "Sector 6", "Sector 9", "Sector 12", "Chas", "Dhanbad Road", "Bokaro Steel City", "Sector 2", "Sector 8", "Sector 15", "Sector 27"),
                "Hazaribagh" to listOf("Hazaribagh City", "Sadar", "Barhi", "Gola", "Koderma", "Barkagaon", "Bihari", "Chouparan", "Ichak", "Jhumri Telaiya", "Katkamsandi", "Tati Jharia", "Kankrej"),
                "Giridih" to listOf("Giridih City", "Dhanwar", "Jamua", "Pirtand", "Gandey", "Tundi", "Bagodar", "Bariyatu", "Maheshtala", "Kumardubi", "Barkagaon", "Dhanwar", "Kumar", "Dumri"),
                "Deoghar" to listOf("Deoghar City", "Raghunathganj", "Madhupur", "Jarmundi", "Kumirdaha", "Karon", "Gopikandar", "Baliapur", "Satsang Nagar", "Jamo", "Katoria", "Sauria", "Pathargama"),
                "Chaibasa" to listOf("Chaibasa City", "Jhinkpani", "Manoharpur", "West Singhbhum", "Seraikela", "Chakradharpur", "Kuchai", "Tonto", "Noamundi", "Tariyani", "Jadugoda", "Dhalbhumgarh", "Baliapur"),
                "Ranchi" to listOf("Ranchi City", "Harmu", "Kanke", "Hatu", "Doranda", "Argora", "Ratu", "Hazaribagh", "Kadamkuan", "Ratu Road", "Main Road", "Upper Bazar", "Bariatu"),
                "Ghatshila" to listOf("Ghatshila City", "Kharagpur", "Raghunathganj", "Dhalbhumgarh", "Jamshedpur", "Chakulia", "Ghatsila", "Jadugoda", "Musabani", "Jugsalai", "Burudih", "Keshpur", "Seraikela"),
                "Koderma" to listOf("Koderma City", "Jhumri Telaiya", "Markachcho", "Satgawan", "Domchanch", "Kumardubi", "Koderma Town", "Koderma Railway Station", "Beltu", "Nawadih", "Karnpura", "Kachur"),
                "Ranchi" to listOf("Ranchi City", "Harmu", "Kanke", "Hatu", "Doranda", "Argora", "Ratu", "Hazaribagh", "Kadamkuan", "Ratu Road", "Main Road", "Upper Bazar", "Bariatu"),
                "Jamshedpur" to listOf("Jamshedpur City", "Bistupur", "Sakchi", "Telco", "Sonari", "Kadma", "Burmamines", "Golmuri", "Gamharia", "Dimna", "Narmada", "Jugsalai", "Adityapur"),
                "Dhanbad" to listOf("Dhanbad City", "Bank More", "Jharia", "Katras", "Sindri", "Koylanagar", "Bharat Coking Coal Limited (BCCL)", "Kumardubi", "Bhuli", "Jorapokhar", "Gandhinagar", "Jharia Township", "Bengal Enamel"),
                "Bokaro" to listOf("Bokaro City", "Sector 4", "Sector 5", "Sector 6", "Sector 9", "Sector 12", "Chas", "Dhanbad Road", "Bokaro Steel City", "Sector 2", "Sector 8", "Sector 15", "Sector 27")
            ),
            "Sikkim" to mapOf(
                "Gangtok" to listOf("Gangtok City", "M.G. Marg", "Namgyal Institute of Tibetology", "Tibet Road", "Lal Market", "Tadong", "Deorali", "Gangtok North", "Gangtok South", "Ranka", "Banjhakri Falls", "Rumtek"),
                "Namchi" to listOf("Namchi Town", "Samdruptse", "Rongyek", "Yangang", "Tendong Hill", "Namchi Bazaar", "Ravangla", "Barkhola", "Chardham", "Bhongkhar", "Pudung", "Sumbuk"),
                "Pelling" to listOf("Pelling Town", "Khecheopalri Lake", "Pemayangtse Monastery", "Sangachoeling Monastery", "Sangram", "Kaluk", "Biksthang", "Geyzing", "Yuksom", "Sanghmon", "Pelling Village", "Mengkhang"),
                "Gyalshing" to listOf("Gyalshing Town", "Khasmarg", "Maling", "Martam", "Tumin", "Rinchenpong", "Sichey", "Kaluk", "Lachung", "Yuksom", "Sikkim Village", "Bikhop"),
                "Rangpo" to listOf("Rangpo Town", "Rangpo Bazaar", "Rangpo Bridge", "Rangpo Border", "Rangpo Village", "Lungchok", "Melli", "Khamdong", "Khamdong Village", "Rangpo North", "Rangpo South"),
                "Jorethang" to listOf("Jorethang Town", "Jorethang Bazaar", "Namchi", "Rangpo", "Daramdin", "Kewzing", "Geyzing", "Sikip", "Khamdong", "Melli", "Sumbuk", "Yuksom"),
                "Rongli" to listOf("Rongli Town", "Rongli Bazaar", "Rongli Village", "Rongli South", "Melli", "Sikkim-Tibet Border", "Rongli North", "Tendong Hill", "Rongli Market", "Khamdong", "Rongli East", "Gyangzong")
            ),
            "Tripura" to mapOf(
                "Agartala" to listOf("Agartala City", "Ujjayanta Palace Area", "Kunjaban", "Gorkhabasti", "Hapania", "Bidhannagar", "Sadar", "Bagbasa", "Nabirgram", "Jirania", "Airport Area", "Rajabari"),
                "Dharmanagar" to listOf("Dharmanagar City", "Bodhjung Nagar", "Nathganj", "Panchgram", "Kanchanpur", "Srinagar", "Ganganagar", "Hathai Chara", "Agaramura", "Khowai", "Kalyanpur", "Udaipur"),
                "Khowai" to listOf("Khowai City", "Khowai Town", "Himalayan", "Ramchandraghat", "Purba Khowai", "Jitpur", "Kumarghat", "Haramoni", "Nirala", "Khamarpara", "Anandabazar", "Rangamati"),
                "Udaipur" to listOf("Udaipur City", "Udaipur District Headquarters", "Ratanpur", "Bishalgarh", "Kamalpur", "Hapania", "Bamachhara", "Rangamati", "Ratanpur", "Anandabazar", "Kalyanpur", "Narsingarh", "Belonia"),
                "Belonia" to listOf("Belonia City", "Belonia District Headquarters", "Hazarama", "Nashik", "Madhupur", "Bishalgarh", "Jirania", "Bamachhara", "Mohanpur", "Kalyanpur", "Narsingarh", "Anandabazar", "Ratanpur"),
                "Kumarghat" to listOf("Kumarghat City", "Kumarghat District Headquarters", "Laxmipur", "Purba Khowai", "Bishalgarh", "Panchgram", "Ganganagar", "Himalayan", "Ramchandraghat", "Jitpur", "Hathai Chara", "Khowai"),
                "Jirania" to listOf("Jirania City", "Jirania District Headquarters", "Bagbasa", "Nabirgram", "Rajabari", "Sadar", "Bidhannagar", "Airport Area", "Agartala", "Kunjaban", "Hapania", "Gorkhabasti"),
                "Kamalpur" to listOf("Kamalpur City", "Kamalpur District Headquarters", "Hapania", "Bamachhara", "Mohanpur", "Rangamati", "Narsingarh", "Belonia", "Anandabazar", "Kalyanpur", "Ratanpur", "Bishalgarh"),
                "Bishalgarh" to listOf("Bishalgarh City", "Bishalgarh District Headquarters", "Ratanpur", "Mohanpur", "Belonia", "Narsingarh", "Kalyanpur", "Khamarpara", "Anandabazar", "Rangamati", "Hapania", "Bagbasa")
            ),
            "Nagaland" to mapOf(
                "Kohima" to listOf("Kohima City", "Kohima Village", "Chiephobozou", "Jotsoma", "Kohima Camp", "Pfütsero", "Phesama", "Merima", "Lerie", "Dzülekie", "Sekrenyi", "Tsütuonuomia"),
                "Dimapur" to listOf("Dimapur City", "Chumukedima", "Niathu Resort Area", "Kohima Road", "Dimapur Airport", "Dhansiripar", "Dimapur District Headquarters", "New Market", "Walford", "East Dimapur", "West Dimapur", "Central Dimapur"),
                "Mokokchung" to listOf("Mokokchung City", "Mokokchung District Headquarters", "Longkhum", "Chuchuyimpang", "Shangnyu", "Mongsenyimti", "Yimyu", "Tuli", "Changtongya", "Mangkolemba", "Khehoi", "Kohima"),
                "Wokha" to listOf("Wokha City", "Wokha District Headquarters", "Bhandari", "Ralan", "Chukitong", "Merapani", "Sanis", "Longsa", "Wokha Town", "Akuk", "Yikhum", "Tseminyu", "Phiro"),
                "Phek" to listOf("Phek City", "Phek District Headquarters", "Chozuba", "Pfutsero", "Meluri", "Kohima-Phek Road", "Sumi", "Phek Town", "Mongsenyimti", "Kizama", "Khehoi", "Yisemyong"),
                "Zunheboto" to listOf("Zunheboto City", "Zunheboto District Headquarters", "Akuluto", "Atoizu", "Satoi", "Phek Road", "Yikhum", "Sumi", "Zunheboto Town", "Longkhim", "Sangtam", "Satakha", "Kohima"),
                "Tuensang" to listOf("Tuensang City", "Tuensang District Headquarters", "Longkhim", "Chare", "Noklak", "Tuensang Town", "Tuensang Village", "Tirap", "Sangtam", "Longkhim", "Sangtsak", "Noklak", "Lima"),
                "Kiphire" to listOf("Kiphire City", "Kiphire District Headquarters", "Pungro", "Sitimi", "Kiphire Town", "Saramati", "Thonoknyu", "Kiphire Village", "Longkhim", "Zunheboto", "Sumi", "Satu"),
                "Mon" to listOf("Mon City", "Mon District Headquarters", "Mon Town", "Mokokchung", "Tizit", "Mon Village", "Longwa", "Mokokchung", "Kongan", "Sangtam", "Mon District Headquarters", "Phomching")
            ),
            "Odisha" to mapOf(
                "Bhubaneswar" to listOf("Lingaraj Temple Area", "Unit-1", "Unit-2", "Unit-4", "Kharavela Nagar", "Sahid Nagar", "Janpath", "Chandrasekharpur", "Baramunda", "Bhubaneswar Railway Station", "Patia", "Infocity"),
                "Cuttack" to listOf("Cuttack City", "Badambadi", "Fergusson College Road", "Mangalabag", "Buxi Bazar", "Link Road", "Naya Sarai", "Gouri Nagar", "Choudhury Bazar", "Ravi Talkies", "Odhisa High Court", "Bengali Street"),
                "Rourkela" to listOf("Rourkela City", "Sector-1", "Sector-2", "Sector-3", "Sector-4", "Sector-5", "Sector-6", "Sector-7", "Bisra", "Vedvyas", "Steel Plant", "Koel Nagar"),
                "Berhampur" to listOf("Berhampur City", "Gosaninuagaon", "Gandhinagar", "Bada Bazar", "Gandhinagar", "Brahmapur", "Chandrapur", "Chhatrapur", "Gop", "Kabisurya Nagar", "Shree Ramachandra Pur", "Mahuriapada"),
                "Balasore" to listOf("Balasore City", "Chhatrabhuja", "Raghunathganj", "Balasore Sadar", "Khardah", "Nayabazar", "Remuna", "Badasahi", "Soro", "Baliapal", "Baharampur", "Jaleswar"),
                "Jharsuguda" to listOf("Jharsuguda City", "Rengali", "Rourkela", "Rajgangpur", "Brajarajnagar", "Kirmira", "Kolabira", "Sundargarh", "Sundargarh Town", "Bargarh", "Malkangiri", "Kesinga"),
                "Khurda" to listOf("Khurda City", "Khurda District Headquarters", "Bhubaneswar-Khurda Road", "Chandaka", "Jatni", "Sundarapada", "Baghamari", "Sankhadi", "Rathipur", "Kulai", "Nuagaon", "Baghuar"),
                "Kalinganagar" to listOf("Kalinganagar", "Brahmanigaon", "Nischintakoili", "Jajpur", "Jajpur Road", "Nimapada", "Sundarapada", "Biridi", "Raghunathpur", "Gopinathpur", "Sukinda", "Dharmasala"),
                "Angul" to listOf("Angul City", "Angul District Headquarters", "Talcher", "Chhendipada", "Kaniha", "Kuchinda", "Pallahara", "Jaduguda", "Jarapada", "Nalco Nagar", "Hinjili", "Rengali"),
                "Rayagada" to listOf("Rayagada City", "Kalyansinghpur", "Bissam Cuttack", "Gumma", "R.Udayagiri", "Nandahandi", "Kashipur", "Bakamunda", "Kondamali", "Chatikona", "J.K.Pur", "Koraput"),
                "Koraput" to listOf("Koraput City", "Koraput District Headquarters", "Jeypore", "Damanjodi", "Laxmipur", "Pottangi", "Semiliguda", "Nandpur", "Kunduli", "Jeypore", "Damanjodi", "Boipariguda", "Borrigumma")
            ),
            "Andhra Pradesh" to mapOf(
                "Visakhapatnam" to listOf("Dwaraka Nagar", "RK Beach", "Benz Circle", "Gajuwaka", "MVP Colony", "Asilmetta", "Tenneti Park", "Jagadamba Junction", "Ram Nagar", "Peddagantyada", "Anandapuram", "Pothinamallayya Palem"),
                "Vijayawada" to listOf("M.G. Road", "Benz Circle", "Vijayawada City", "Eluru Road", "Rajiv Gandhi Park", "Rayanapadu", "Suryaraopet", "Bhavanipuram", "Kanuru", "Machavaram", "Mangalagiri", "Peddaganjam"),
                "Guntur" to listOf("Guntur City", "Brodi", "Lakshmipuram", "Arundelpet", "Mangalagiri", "Guntur West", "Guntur East", "Nallapadu", "Chilakaluripet", "Pedakakani", "Tenali", "Prathipadu"),
                "Kakinada" to listOf("Kakinada City", "Rly. Junction", "Rajamahendravaram", "Sarpavaram", "Vakalapudi", "Balayapalli", "Jeevakona", "Nagulapadu", "Kakinada Port", "Gollaprolu", "Kakinada Beach", "Pedapudi"),
                "Tirupati" to listOf("Tirupati City", "Tirumala", "Chandragiri", "Nellore", "Renigunta", "Akkarampalle", "Srinivasa Nagar", "Siddhartha Medical College", "Tiruchanur", "Srikalahasti", "Puttur", "Karvet Nagar"),
                "Anantapur" to listOf("Anantapur City", "Anantapuram", "Guntakal", "Rayadurg", "Peddapalli", "Dharmavaram", "Kalyandurg", "Tadipatri", "Penukonda", "Bukkapatnam", "Puttaparthi", "Hindupur"),
                "Rajahmundry" to listOf("Rajahmundry City", "Rajanagaram", "Gokavaram", "Korukonda", "Jaggampeta", "Amalapuram", "Ravulapalem", "Kadiyam", "Nidadavolu", "Mandapeta", "Gollaprolu", "Nallajerla"),
                "Nellore" to listOf("Nellore City", "Nellore East", "Nellore West", "Sullurpet", "Venkatagiri", "Kavali", "Gudur", "Naidupeta", "Allur", "Udayagiri", "Mypadu", "Rachur"),
                "Chittoor" to listOf("Chittoor City", "Tirupati", "Puttur", "Tiruchanur", "Srikalahasti", "Renigunta", "Peddaganjam", "Chandragiri", "Nagari", "Vayalpadu", "Palamaner", "Baireddipalle"),
                "Kurnool" to listOf("Kurnool City", "Nandikotkur", "Adoni", "Dhone", "Atmakur", "Yemmiganur", "Peddapalli", "Jupadu Bungalow", "Kallur", "Nallamada", "Maha Narayanapuram", "Alur", "Banaganapalle"),
                "Kadapa" to listOf("Kadapa City", "Pulivendula", "Proddatur", "Jammalamadugu", "Kampli", "Chennur", "Mydukur", "Rayachoti", "Kadapa", "Siddavatam", "Kodur", "Mylavaram"),
                "Srikakulam" to listOf("Srikakulam City", "Srikakulam District Headquarters", "Tekkali", "Palakonda", "Ichchapuram", "Hiramandalam", "Amadalavalasa", "Narasannapeta", "Rajam", "Sarubujjili", "Pathapatnam", "Sompeta")
            ),
            "Manipur" to mapOf(
                "Imphal" to listOf("Imphal City", "Lambulane", "Thangmeiband", "Kangla Fort", "Hiyangthang", "Singjamei", "Mongshangei", "Keishampat", "Polo Ground", "Bamon Leikai", "Chingmeirong", "Khurai"),
                "Churachandpur" to listOf("Churachandpur City", "Tuibong", "Moreh", "Henglep", "Lunglei", "Saikot", "Churachandpur Market", "Rengkai", "Khawbung", "Mualbam", "Sangaiprou", "Lamka"),
                "Kakching" to listOf("Kakching City", "Kakching Khunou", "Kakching Lamkhai", "Kakching Khunou", "Kakching Wairi", "Kakching Turel", "Kakching Karam", "Kakching", "Chakpikarong", "Sora", "Chakpikarong"),
                "Thoubal" to listOf("Thoubal City", "Thoubal Okram", "Thoubal Chingkham", "Thoubal Leikai", "Thoubal Wangjing", "Thoubal Khunou", "Thoubal Khewa", "Thoubal Moirang", "Yairipok", "Ningthoukhong", "Kangpokpi"),
                "Senapati" to listOf("Senapati City", "Senapati District Headquarters", "Mao", "Maram", "Pongring", "Khubi", "Laii", "Oinam", "Sajik Tampak", "Ngamju", "Lairong", "Khurai"),
                "Ukhrul" to listOf("Ukhrul City", "Ukhrul District Headquarters", "Hungpung", "Litan", "Khamasom", "Phungyar", "Tungjoy", "Tolloi", "Malsi", "Khurai", "Chamu", "Chingai"),
                "Tamenglong" to listOf("Tamenglong City", "Tamenglong District Headquarters", "Tamei", "Noney", "Khongsang", "Nungba", "Bongmual", "Litan", "Jiribam", "Kangpokpi", "Mao", "Sajik Tampak"),
                "Jiribam" to listOf("Jiribam City", "Jiribam District Headquarters", "Jiribam Market", "Nungba", "Laitung", "Kangpokpi", "Mao", "Tamei", "Khongsang", "Jiri", "Tengnoupal", "Churachandpur"),
                "Bishnupur" to listOf("Bishnupur City", "Bishnupur District Headquarters", "Moirang", "Nambol", "Oinam", "Kwakta", "Lamboi Khongnangkhong", "Nongdam", "Bishnupur", "Kumbi", "Khongjom", "Thanga"),
                "Kangpokpi" to listOf("Kangpokpi City", "Kangpokpi District Headquarters", "Kangpokpi Market", "Kangpokpi", "Kangchup", "Sajik Tampak", "Phungyar", "Tamei", "Nungba", "Chamu", "Lairong"),
                "Pherzawl" to listOf("Pherzawl City", "Pherzawl District Headquarters", "Pherzawl", "Saikul", "Sajik Tampak", "Mao", "Tamei", "Lairong", "Phungyar", "Khongsang", "Nungba", "Tengnoupal")
            ),
            "Assam" to mapOf(
                "Guwahati" to listOf("Guwahati City", "Fancy Bazaar", "Paltan Bazar", "Kahilipara", "Dispur", "Ganeshguri", "Chandmari", "Uzan Bazar", "Maligaon", "Jalukbari", "Beltola", "R.G. Baruah Road"),
                "Dibrugarh" to listOf("Dibrugarh City", "Dibrugarh University", "Naliapool", "Lachit Nagar", "Dibrugarh Airport", "Nimati Ghat", "Chabua", "Naharkatia", "Joypur", "Lahoal", "Tingkhong", "Khowang"),
                "Silchar" to listOf("Silchar City", "Cachar", "Sonai", "Kumargram", "Badarpur", "Kaliabor", "Lakhipur", "Hailakandi", "Karimganj", "Dullavcherra", "Chandpur", "Kalain"),
                "Jorhat" to listOf("Jorhat City", "Jorhat University", "Mokokchung Road", "Dhekiajuli", "Teok", "Koliapani", "Nimati", "Majuli", "Rowriah", "Garmur", "Gohain Gaon", "Rangpur"),
                "Nagaon" to listOf("Nagaon City", "Hojai", "Boko", "Raha", "Kachua", "Jakhalabandha", "Nagaon", "Lumding", "Batadrava", "Kaliabor", "Hojai", "Kothiatoli", "Koroitoli"),
                "Tinsukia" to listOf("Tinsukia City", "Tinsukia Railway Station", "Doomdooma", "Margherita", "Digboi", "Makum", "Borhat", "Chabua", "Sadiya", "Namsai", "Gohpur", "Halam"),
                "Karimganj" to listOf("Karimganj City", "Ratabari", "Patharkandi", "Kalimganj", "Badarpur", "Karimganj Town", "Kushwath", "Patherkandi", "Bhanga", "Kaliganj", "Kachudaram", "Siddhinagar"),
                "Hailakandi" to listOf("Hailakandi City", "Lala", "Katlicherra", "Hailakandi District", "Silchar", "Badarpur", "Dullavcherra", "Kalain", "Chandpur", "Narsingpur", "Bilasipara", "Ramchandrapur"),
                "Darrang" to listOf("Mangaldai", "Dalgaon", "Sipajhar", "Ramgaon", "Pachaspada", "Kharupetia", "Bhergaon", "Harisinga", "Kundil", "Darrang Town", "Harpur", "Bongalgaon"),
                "Sonitpur" to listOf("Tezpur", "Gohpur", "Bihpuria", "Nagaon", "Biswanath Chariali", "Dhekiajuli", "Kampur", "Bamgaon", "Sadiya", "Dhemaji", "Lakhimpur", "Borchala"),
                "Bongaigaon" to listOf("Bongaigaon City", "Bongaigaon Railway Station", "Srirampur", "Abhayapuri", "New Bongaigaon", "Bijni", "Dhamdhama", "Mowamari", "Kachomari", "Bogribari", "Panigaon", "Kachari"),
                "Barpeta" to listOf("Barpeta City", "Barpeta Road", "Salbari", "Sarthebari", "Pathshala", "Bhurbandha", "Kalgachia", "Gomaphulbari", "Kolaigaon", "Puthimari", "Urmila", "Ramchandrapur"),
                "Dhemaji" to listOf("Dhemaji City", "Sissiborgaon", "Jonai", "Mekori", "Lakhimpur", "Kachamari", "Rongapara", "Dambuk", "Sangram", "Gohpur", "Laluk", "Kaliabor"),
                "Golaghat" to listOf("Golaghat City", "Dergaon", "Khumtai", "Kamarbandha", "Sivsagar", "Ghiladhari", "Bokakhat", "Numaligarh", "Jorhat", "Kothiatoli", "Ukruli", "Bohori"),
                "Kokrajhar" to listOf("Kokrajhar City", "Gossaigaon", "Bijni", "Kachugaon", "Bongaigaon", "Salbari", "Kachari", "Habraghat", "Bhotgaon", "Baruapara", "Dhubri", "Kokrajhar District")
            ),
            "Goa" to mapOf(
                "Panaji" to listOf("Panaji City", "Altinho", "Campal", "Panjim", "Taleigao", "St. Inez", "Miramar", "Mala", "Nerul", "Cortalim", "Tivim", "Santa Cruz", "Panjim Market"),
                "Vasco da Gama" to listOf("Vasco City", "Dabolim", "Mormugao", "Baina", "Baina Beach", "New Vaddem", "Vaddem", "Cortalim", "Sancoale", "Marmagao", "Shantinagar", "Chicalim", "Goa Shipyard"),
                "Margao" to listOf("Margao City", "Fatorda", "Cortalim", "Raia", "Benaulim", "Navelim", "Madgaon", "Seraulim", "Cortalim", "Cortalim", "Davorlim", "Verna", "Majorda"),
                "Mapusa" to listOf("Mapusa City", "Anjuna", "Baga", "Calangute", "Aldona", "Parra", "Pernem", "Agarwada", "Cortalim", "Vagator", "Siolim", "Saligao", "Mapusa Market"),
                "Ponda" to listOf("Ponda City", "Mardol", "Kumarapatnam", "Shiroda", "Farmagudi", "Khandola", "Borkhar", "Nerul", "Sankhali", "Malar", "Madkai", "Santoshi", "Cortalim"),
                "Quepem" to listOf("Quepem City", "Canacona", "Cortalim", "Sanguem", "Molem", "Karmali", "Korgao", "Cortalim", "Palolem", "Chaudi", "Cortalim", "Sanguem", "Netravali"),
                "Bicholim" to listOf("Bicholim City", "Bicholim", "Keri", "Pernem", "Sattari", "Mapusa", "Vagator", "Saligao", "Siolim", "Parra", "Assagao", "Calangute", "Nerul"),
                "Sanquelim" to listOf("Sanquelim City", "Sanquelim", "Sulcorna", "Assonora", "Maina", "Bicholim", "Keri", "Dattawadi", "Nerul", "Cortalim", "Karmali", "Pernem", "Baga"),
                "Aldona" to listOf("Aldona City", "Aldona", "Corjuem", "Moira", "Pernem", "Siolim", "Assagao", "Saligao", "Parra", "Mapusa", "Calangute", "Vagator", "Baga"),
                "Chicalim" to listOf("Chicalim City", "Chicalim", "Dabolim", "Vasco", "Marmagao", "Navelim", "Dabolim", "Benaulim", "Cortalim", "Vaddem", "Marmagao", "New Vaddem", "Shantinagar"),
                "Verna" to listOf("Verna City", "Verna", "Cortalim", "Raia", "Sancoale", "Benaulim", "Salcete", "Madgaon", "Navelim", "Vasco", "Chicalim", "Dabolim", "Cortalim")
            ),
            "Chhattisgarh" to mapOf(
                "Raipur" to listOf("Raipur City", "Shankar Nagar", "Dharampura", "Moudhapara", "Ganjpara", "Kachori Gali", "Telibandha", "Vivekanand Nagar", "VIP Road", "Civil Lines", "Khamardih", "Sunder Nagar"),
                "Bhilai" to listOf("Bhilai Nagar", "Sector 1", "Sector 6", "Sector 7", "Sector 9", "Sector 10", "Sector 11", "Sector 12", "Sector 15", "Sector 17", "Sector 19", "Durg", "Kachna"),
                "Durg" to listOf("Durg City", "Patan", "Bhilai", "Bemetara", "Chhapra", "Nandini", "Fatehpur", "Nandini", "Sakti", "Balod", "Kurud", "Rajnandgaon", "Bhilai Steel Plant"),
                "Bilaspur" to listOf("Bilaspur City", "Civil Lines", "Vikash Nagar", "Kachh", "Goverdhan", "Chhoti Sarai", "Jangla", "Pendra", "Takhatpur", "Bhatapara", "Naila", "Janakpur", "Ratanpur"),
                "Korba" to listOf("Korba City", "Marwa", "Katghora", "Pali", "Jamanipali", "Bhatgaon", "Korba Township", "Dipka", "Champa", "Dabhra", "Chhattisgarh", "Shivpur", "Jamanipali"),
                "Raigarh" to listOf("Raigarh City", "Raigarh Railway Colony", "Jindal Nagar", "Kharasia", "Gharghoda", "Saragaon", "Kharsia", "Champa", "Lailunga", "Tamar", "Birsinghpur", "Lormi", "Ratanpur"),
                "Rajnandgaon" to listOf("Rajnandgaon City", "Dongargaon", "Khairagarh", "Chhindgarh", "Kundala", "Kholi", "Gondia", "Patan", "Gongla", "Durg", "Kanker", "Kondagaon", "Nayapara"),
                "Janjgir-Champa" to listOf("Janjgir City", "Champa", "Pathalgaon", "Naila", "Janjgir", "Pamgarh", "Malkharoda", "Malkharoda", "Akaltara", "Naila", "Kharod", "Takhatpur", "Sakti"),
                "Kabirdham" to listOf("Kabra", "Bhabhra", "Bari", "Rajnandgaon", "Durg", "Khairagarh", "Kurud", "Kondagaon", "Kanker", "Nayapara", "Dabhra", "Raigarh", "Ratanpur"),
                "Bemetara" to listOf("Bemetara City", "Mungeli", "Simga", "Nandini", "Kawardha", "Nainpur", "Mohanpur", "Rajnandgaon", "Patan", "Pithora", "Bhatapara", "Kurud", "Gariaband"),
                "Gariaband" to listOf("Gariaband City", "Sargi", "Bhadrapur", "Khandapara", "Pithora", "Kusmi", "Mohanpur", "Bhatapara", "Rajnandgaon", "Patan", "Dhamtari", "Baloda Bazar", "Kanker"),
                "Dhamtari" to listOf("Dhamtari City", "Nagri", "Sukma", "Janjgir", "Champa", "Patan", "Pithora", "Gariaband", "Raipur", "Bilaspur", "Rajnandgaon", "Durg", "Bemetara", "Mungeli"),
                "Sukma" to listOf("Sukma City", "Kondagaon", "Dhamtari", "Kanker", "Jagdalpur", "Narayanpur", "Dantewada", "Gariaband", "Bijapur", "Raipur", "Janjgir", "Champa", "Bilaspur")
            )
        ),
        "Russia" to mapOf(
            "Moscow" to mapOf(
                "Central Administrative Okrug" to listOf("Arbat", "Khamovniki", "Tverskoy", "Tagansky", "Basmanny"),
                "North-Eastern Administrative Okrug" to listOf("Sviblovo", "Ostankino", "Aleksandrovsky Park", "Biryulyovo", "Marfino"),
                "South-Eastern Administrative Okrug" to listOf("Kuzminki", "Lyublino", "Vykhino-Zhulebino", "Nagatinsky Zaton", "Zyablikovo"),
                "South-Western Administrative Okrug" to listOf("Gagarinsky", "Yakovlevsky", "Khamovniki", "Troparevo-Nikulino"),
                "Western Administrative Okrug" to listOf("Krylatskoye", "Ochakovo-Matveevskoye", "Vnukovo", "Mosfilmovskaya"),
                "North-Western Administrative Okrug" to listOf("Vyborgsky", "Krestovsky Island", "Primorsky"),
                "Eastern Administrative Okrug" to listOf("Perovo", "Novogireyevo", "Novokosino", "Veshnyaki")
            ),
            "Saint Petersburg" to mapOf(
                "Central District" to listOf("Nevsky Prospekt", "Admiralteysky", "Tsentralny"),
                "Vasileostrovsky District" to listOf("Vasilievsky Island", "Russian Museum"),
                "Petrogradsky District" to listOf("Petrogradskaya Side", "Gorkovskaya"),
                "Kalininsky District" to listOf("Kalininsky", "Vyborgskaya"),
                "Frunzensky District" to listOf("Frunzensky", "Moskovskaya")
            ),
            "Yekaterinburg" to mapOf(
                "Central District" to listOf("Vysotsky", "Yekaterinburg City Center", "Leninsky"),
                "Kirovsky District" to listOf("Kirovsky", "Uralmash", "Yuzhnoye"),
                "Leninsky District" to listOf("Leninsky", "Yekaterinburg City Center"),
                "Oktyabrsky District" to listOf("Oktyabrsky", "Airport", "Metallurg")
            )
        ),
        "China" to mapOf(
            "Beijing" to mapOf(
                "Dongcheng District" to listOf("Tiananmen Square", "Wangfujing", "Qianmen"),
                "Xicheng District" to listOf("Xidan", "Beihai", "Jishuitan"),
                "Chaoyang District" to listOf("CBD", "Sanlitun", "Olympic Park"),
                "Haidian District" to listOf("Peking University", "Tsinghua University", "Zhongguancun"),
                "Fengtai District" to listOf("Fengtai", "Majiabao"),
                "Shijingshan District" to listOf("Shijingshan", "Jianxiang")
            ),

            "Shanghai" to mapOf(
                "Huangpu District" to listOf("The Bund", "Nanjing Road", "People's Square"),
                "Pudong New District" to listOf("Lujiazui", "Pudong Airport", "Century Park"),
                "Xuhui District" to listOf("Xujiahui", "Tianzifang", "Donghua University"),
                "Jingan District" to listOf("Nanjing West Road", "Jingan Temple"),
                "Changning District" to listOf("Hongqiao", "Gubei")
            ),

            "Guangzhou" to mapOf(
                "Yuexiu District" to listOf("Beijing Road", "Shamian Island", "Canton Tower"),
                "Tianhe District" to listOf("Tianhe Sports Center", "Zhujiang New Town"),
                "Haizhu District" to listOf("Haizhu Bridge", "Canton Fair Complex"),
                "Liwan District" to listOf("Liwan Plaza", "Chimelong Paradise")
            ),

            "Shenzhen" to mapOf(
                "Luohu District" to listOf("Luohu Port", "Dongmen", "KK Mall"),
                "Futian District" to listOf("Civic Center", "Futian Port"),
                "Nanshan District" to listOf("Shenzhen Bay", "Shekou", "University Town"),
                "Bao'an District" to listOf("Bao'an Center", "Qianhai")
            )
        ),
        "England" to mapOf(
            "London" to mapOf(
                "Westminster" to listOf("Buckingham Palace", "Big Ben", "Westminster Abbey"),
                "Camden" to listOf("Camden Market", "Regent's Park"),
                "Kensington and Chelsea" to listOf("Kensington Palace", "Chelsea Harbour"),
                "City of London" to listOf("The Gherkin", "Tower of London"),
                "Southwark" to listOf("Borough Market", "London Bridge")
            ),

            "Manchester" to mapOf(
                "City Centre" to listOf("Arndale Centre", "Northern Quarter"),
                "Salford" to listOf("Salford Quays", "MediaCityUK"),
                "Didsbury" to listOf("Didsbury Village", "Burton Road"),
                "Chorlton" to listOf("Chorlton Green", "Beesley Green")
            ),

            "Birmingham" to mapOf(
                "City Centre" to listOf("Bullring", "Birmingham Museum and Art Gallery"),
                "Edgbaston" to listOf("Edgbaston Cricket Ground", "University of Birmingham"),
                "Kings Heath" to listOf("Kings Heath Park", "High Street"),
                "Balsall Heath" to listOf("Balsall Heath Park", "Moseley Road")
            )
        ),
        "Japan" to mapOf(
            "Tokyo" to mapOf(
                "Chiyoda" to listOf("Imperial Palace", "Akihabara", "Yasukuni Shrine"),
                "Shibuya" to listOf("Shibuya Crossing", "Harajuku", "Shibuya Station"),
                "Shinjuku" to listOf("Kabukicho", "Tokyo Metropolitan Government Building", "Golden Gai"),
                "Minato" to listOf("Roppongi", "Tokyo Tower", "Odaiba"),
                "Taito" to listOf("Asakusa", "Ueno Park", "Ameyoko")
            ),

            "Osaka" to mapOf(
                "Namba" to listOf("Dotonbori", "Shinsaibashi", "Namba Parks"),
                "Umeda" to listOf("Umeda Sky Building", "Hankyu Department Store"),
                "Tennoji" to listOf("Tennoji Park", "Abeno Harukas"),
                "Kyobashi" to listOf("Osaka Castle", "Kyobashi Station")
            ),

            "Kyoto" to mapOf(
                "Higashiyama" to listOf("Kiyomizu-dera", "Gion District", "Philosopher's Path"),
                "Gion" to listOf("Gion Corner", "Yasaka Shrine"),
                "Arashiyama" to listOf("Bamboo Grove", "Tenryu-ji Temple"),
                "Central Kyoto" to listOf("Nijo Castle", "Kinkaku-ji")
            ),

            "Fukuoka" to mapOf(
                "Hakata" to listOf("Hakata Station", "Canal City", "Fukuoka Tower"),
                "Tenjin" to listOf("Tenjin Central Park", "IMS Building"),
                "Nakasu" to listOf("Nakasu District", "Kushida Shrine")
            )
        ),
        "Australia" to mapOf(
            "Sydney" to mapOf(
                "Sydney CBD" to listOf("Circular Quay", "The Rocks", "Martin Place"),
                "Bondi" to listOf("Bondi Beach", "Bondi Junction"),
                "Manly" to listOf("Manly Beach", "Corso"),
                "Newtown" to listOf("King Street", "Newtown Festival"),
                "Surry Hills" to listOf("Crown Street", "Central Station")
            ),

            "Melbourne" to mapOf(
                "Central Business District (CBD)" to listOf("Federation Square", "Queen Victoria Market"),
                "Fitzroy" to listOf("Brunswick Street", "Fitzroy Gardens"),
                "St Kilda" to listOf("St Kilda Beach", "Luna Park"),
                "Southbank" to listOf("Southbank Promenade", "Arts Centre Melbourne"),
                "Docklands" to listOf("Marvel Stadium", "Harbour Town")
            ),

            "Brisbane" to mapOf(
                "City Centre" to listOf("Queen Street Mall", "South Bank"),
                "Fortitude Valley" to listOf("James Street", "China Town"),
                "West End" to listOf("Boundary Street", "South Brisbane"),
                "South Bank" to listOf("South Bank Parklands", "Queensland Art Gallery")
            ),

            "Perth" to mapOf(
                "Central Business District" to listOf("Elizabeth Quay", "Kings Park"),
                "Northbridge" to listOf("Northbridge Piazza", "Fremantle Markets"),
                "Fremantle" to listOf("Fremantle Harbour", "Fremantle Markets"),
                "Subiaco" to listOf("Subiaco Oval", "Rokeby Road")
            )
        )
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        val firstNameField = findViewById<EditText>(R.id.first_name_field)
        val lastNameField = findViewById<EditText>(R.id.last_name_field)
        val emailField = findViewById<EditText>(R.id.email_field)
        val passwordField = findViewById<EditText>(R.id.password_field)
        val confirmPasswordField = findViewById<EditText>(R.id.confirm_password_field)
        countrySpinner = findViewById<Spinner>(R.id.country_spinner)
        stateSpinner = findViewById<Spinner>(R.id.state_spinner).apply { visibility = View.GONE }
        citySpinner = findViewById<Spinner>(R.id.city_spinner).apply { visibility = View.GONE }
        localitySpinner = findViewById<Spinner>(R.id.locality_spinner).apply { visibility = View.GONE }
        val showPasswordButton = findViewById<ImageView>(R.id.show_password_button)
        val showConfirmPasswordButton = findViewById<ImageView>(R.id.show_confirm_password_button)
        val registerAccountButton = findViewById<Button>(R.id.register_account_button)

        showPasswordButton.setOnClickListener {
            togglePasswordVisibility(passwordField)
        }

        showConfirmPasswordButton.setOnClickListener {
            togglePasswordVisibility(confirmPasswordField)
        }

        setupCountrySpinner()

        registerAccountButton.setOnClickListener {
            registerAccount(
                firstNameField,
                lastNameField,
                emailField,
                passwordField,
                confirmPasswordField
            )
        }
    }

    private fun togglePasswordVisibility(passwordField: EditText) {
        val isVisible = passwordField.inputType == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
        passwordField.inputType = if (isVisible) {
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        } else {
            InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
        }
        passwordField.setSelection(passwordField.text.length)
    }

    private fun setupCountrySpinner() {
        val countryAdapter = ArrayAdapter<String>(
            this,
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
            countries.keys.sorted()
        )
        countryAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item)
        countrySpinner.adapter = countryAdapter

        countrySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                stateSpinner.visibility = View.VISIBLE
                val selectedCountry = parent?.getItemAtPosition(position) as String
                setupStateSpinner(selectedCountry)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun setupStateSpinner(selectedCountry: String) {
        val states = countries[selectedCountry]?.keys?.toList()?.sorted() ?: emptyList()
        val stateAdapter = ArrayAdapter<String>(
            this,
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
            states
        )
        stateAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item)
        stateSpinner.adapter = stateAdapter

        stateSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                citySpinner.visibility = View.VISIBLE
                val selectedState = parent?.getItemAtPosition(position) as String
                setupCitySpinner(selectedCountry, selectedState)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun setupCitySpinner(selectedCountry: String, selectedState: String) {
        val cities: List<String> =
            countries[selectedCountry]?.get(selectedState)?.keys?.toList()?.sorted() ?: emptyList()
        val cityAdapter = ArrayAdapter<String>(
            this,
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
            cities.toTypedArray()
        )
        cityAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item)
        citySpinner.adapter = cityAdapter

        citySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                localitySpinner.visibility = View.VISIBLE
                val selectedCity = parent?.getItemAtPosition(position) as String
                setupLocalitySpinner(selectedCountry, selectedState, selectedCity)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun setupLocalitySpinner(
        selectedCountry: String,
        selectedState: String,
        selectedCity: String
    ) {
        val localities: List<String> = (countries[selectedCountry]?.get(selectedState)?.get(selectedCity) as? List<String>)?.sorted() ?: emptyList()
        val localityAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            localities
        )

        localityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        localitySpinner.adapter = localityAdapter
    }

    private fun registerAccount(
        firstNameField: EditText,
        lastNameField: EditText,
        emailField: EditText,
        passwordField: EditText,
        confirmPasswordField: EditText
    ) {
        val firstName = firstNameField.text.toString()
        val lastName = lastNameField.text.toString()
        val email = emailField.text.toString().trim()
        val password = passwordField.text.toString().trim()
        val confirmPassword = confirmPasswordField.text.toString().trim()
        val country = countrySpinner.selectedItem.toString()
        val state = stateSpinner.selectedItem.toString()
        val city = citySpinner.selectedItem.toString()
        val locality = localitySpinner.selectedItem.toString()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Email and password cannot be empty", Toast.LENGTH_SHORT).show()
            return
        }

        if (password != confirmPassword) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            return
        }

        registerAccount(email, password, firstName, lastName, country, state, city, locality)
    }

    private fun registerAccount(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        country: String,
        state: String,
        city: String,
        locality: String
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = auth.currentUser?.uid
                    val userData = mapOf(
                        "firstName" to firstName,
                        "lastName" to lastName,
                        "email" to email,
                        "address" to mapOf(
                            "country" to country,
                            "state" to state,
                            "city" to city,
                            "locality" to locality
                        )
                    )

                    db.collection("login").document(userId.toString()).set(userData)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(
                                    this,
                                    "Account created successfully",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                Toast.makeText(this, "Failed to create account", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                } else {
                    Toast.makeText(this, "Failed to create account", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
