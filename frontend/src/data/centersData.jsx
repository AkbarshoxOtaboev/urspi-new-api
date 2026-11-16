import React from 'react';
import {
  BookOpen, GraduationCap, Library, Users, Award, Heart, Sparkles, Star, Scale,
  Globe, Shield, Monitor, Calculator, FileText, Landmark, ShieldCheck,
  TrendingUp, UserCheck, Briefcase, Cpu, Building, Stethoscope, Microscope,
  FolderCheck, Trophy, Camera, Megaphone, Zap, Compass, Flame, Target,
  Coins, Database, Printer, Radio, Lock, Wrench, Truck, PieChart, Layers, Building2
} from 'lucide-react';

export const initialCenters = [];

export const DEFAULT_CENTERS_SEED_DATA = [
  {
    nameUz: "Ta'lim sifatini nazorat qilish bo'limi",
    nameRu: "Отдел контроля качества образования",
    nameEn: "Education Quality Control Department",
    descriptionUz: "Ta'lim jarayoni va ta'lim sifatini muntazam monitoring hamda tahlil qilish bo'limi.",
    descriptionRu: "Отдел регулярного мониторинга и анализа учебного процесса и качества образования.",
    descriptionEn: "Department for regular monitoring and analysis of the educational process and education quality.",
    iconName: "PieChart"
  },
  {
    nameUz: "Ilmiy tadqiqotlar, innovatsiyalar va ilmiy-pedagog kadrlar tayyorlash bo'limi",
    nameRu: "Отдел научных исследований, инноваций и подготовки научно-педагогических кадров",
    nameEn: "Scientific Research, Innovation and Scientific-Pedagogical Staff Training Department",
    descriptionUz: "Ilmiy-tadqiqot loyihalarini muvofiqlashtirish, innovatsion faoliyat va ilmiy kadrlar tayyorlash.",
    descriptionRu: "Координация научно-исследовательских проектов, инновационная деятельность и подготовка научных кадров.",
    descriptionEn: "Coordination of scientific research projects, innovative activities, and training of scientific staff.",
    iconName: "GraduationCap"
  },
  {
    nameUz: "O'quv uslubiy boshqarmasi",
    nameRu: "Учебно-методическое управление",
    nameEn: "Educational and Methodological Department",
    descriptionUz: "Institutdagi o'quv rejalari, dasturlari va ta'lim jarayonini metodik jihatdan muvofiqlashtirish.",
    descriptionRu: "Учебно-методическое сопровождение и координация учебных планов и программ института.",
    descriptionEn: "Educational and methodological support and coordination of institute curricula and programs.",
    iconName: "Library"
  },
  {
    nameUz: "Jismoniy va yuridik shaxslarning murojaatlari bilan ishlash, nazorat va monitoring bo'limi",
    nameRu: "Отдел по работе с обращениями физических и юридических лиц, контроля и мониторинга",
    nameEn: "Department for Appeals of Physical and Legal Entities, Control and Monitoring",
    descriptionUz: "Fuqarolar va yuridik shaxslarning murojaatlarini o'z vaqtida ko'rib chiqish va ijro intizomi monitoringi.",
    descriptionRu: "Своевременное рассмотрение обращений граждан и юридических лиц, мониторинг исполнительской дисциплины.",
    descriptionEn: "Timely review of appeals from citizens and legal entities, monitoring execution discipline.",
    iconName: "Users"
  },
  {
    nameUz: "Magistratura bo'limi",
    nameRu: "Отдел магистратуры",
    nameEn: "Master's Department",
    descriptionUz: "Magistratura mutaxassisliklari bo'yicha ta'lim jarayonini tashkil etish va muvofiqlashtirish.",
    descriptionRu: "Организация и координация учебного процесса по специальностям магистратуры.",
    descriptionEn: "Organization and coordination of the educational process for Master's degree specialties.",
    iconName: "Award"
  },
  {
    nameUz: "Xodimlar bo'limi",
    nameRu: "Отдел кадров",
    nameEn: "Human Resources Department",
    descriptionUz: "Professor-o'qituvchilar va hodimlarning shaxsiy tarkibi hisobini yuritish va mehnat munosabatlari.",
    descriptionRu: "Ведение учета личного состава профессорско-преподавательского состава и сотрудников, трудовые отношения.",
    descriptionEn: "Maintaining personnel records of teaching staff and employees, managing labor relations.",
    iconName: "UserCheck"
  },
  {
    nameUz: "Yoshlar bilan ishlash va ma'naviyat-ma'rifat bo'limi",
    nameRu: "Отдел по работе с молодежью, духовности и просвещения",
    nameEn: "Youth Work, Spirituality and Enlightenment Department",
    descriptionUz: "Talabalarning ma'naviy-ma'rifiy dunyoqarashini shakllantirish hamda yoshlar tashabbuslarini qo'llab-quvvatlash.",
    descriptionRu: "Формирование духовно-просветительского мировоззрения студентов и поддержка молодежных инициатив.",
    descriptionEn: "Developing spiritual and educational outlook of students and supporting youth initiatives.",
    iconName: "Sparkles"
  },
  {
    nameUz: "Iqtidorli talabalar bilan ishlash bo'limi",
    nameRu: "Отдел по работе с одаренными студентами",
    nameEn: "Department for Work with Talented Students",
    descriptionUz: "Iqtidorli talabalarni aniqlash, olimpiada va davlat stipendiyalariga tayyorlash.",
    descriptionRu: "Выявление одаренных студентов, подготовка к олимпиадам и государственным стипендиям.",
    descriptionEn: "Identifying talented students, preparing them for Olympiads and state scholarships.",
    iconName: "Star"
  },
  {
    nameUz: "Yurist konsult bo'limi",
    nameRu: "Юрисконсультский отдел",
    nameEn: "Legal Counsel Department",
    descriptionUz: "Institut faoliyatini huquqiy ta'minlash, shartnomalar va huquqiy hujjatlar ekspertizasi.",
    descriptionRu: "Правовое обеспечение деятельности института, экспертиза договоров и правовых документов.",
    descriptionEn: "Legal support of institute activities, expertise of contracts and legal documents.",
    iconName: "Scale"
  },
  {
    nameUz: "Xalqaro aloqalar bo'limi",
    nameRu: "Отдел международных связей",
    nameEn: "International Relations Department",
    descriptionUz: "Xorijiy oliy ta'lim muassasalari va xalqaro tashkilotlar bilan hamkorlikni yo'lga qo'yish hamda rivojlantirish.",
    descriptionRu: "Установление и развитие сотрудничества с зарубежными высшими учебными заведениями и международными организациями.",
    descriptionEn: "Establishing and developing cooperation with foreign higher education institutions and international organizations.",
    iconName: "Globe"
  },
  {
    nameUz: "Kasaba uyushma qo'mitasi",
    nameRu: "Профсоюзный комитет",
    nameEn: "Trade Union Committee",
    descriptionUz: "Xodimlar va talabalarning mehnat hamda ijtimoiy-iqtisodiy huquq va manfaatlarini himoya qilish.",
    descriptionRu: "Защита трудовых и социально-экономических прав и интересов сотрудников и студентов.",
    descriptionEn: "Protection of labor and socio-economic rights and interests of employees and students.",
    iconName: "Shield"
  },
  {
    nameUz: "Raqamli ta'lim texnologiyalari markazi",
    nameRu: "Центр цифровых образовательных технологий",
    nameEn: "Digital Educational Technologies Center",
    descriptionUz: "Raqamli ta'lim texnologiyalarini joriy etish, elektron ta'lim resurslarini yaratish va ta'lim jarayonini raqamlashtirishni ta'minlash.",
    descriptionRu: "Внедрение цифровых образовательных технологий, создание электронных образовательных ресурсов и цифровизация учебного процесса.",
    descriptionEn: "Implementation of digital educational technologies, creation of e-learning resources and digitalization of the learning process.",
    iconName: "Monitor"
  },
  {
    nameUz: "Buxgalteriya va moliya reja",
    nameRu: "Отдел бухгалтерии и финансового планирования",
    nameEn: "Accounting and Financial Planning Department",
    descriptionUz: "Institut moliya-xo'jalik faoliyati hisobini yuritish va smeta ijrosini rejalashtirish.",
    descriptionRu: "Ведение учета финансово-хозяйственной деятельности института и планирование исполнения сметы.",
    descriptionEn: "Accounting for financial and economic activities of the institute and planning budget execution.",
    iconName: "Calculator"
  },
  {
    nameUz: "Axborot resurs markazi",
    nameRu: "Информационно-ресурсный центр",
    nameEn: "Information Resource Center",
    descriptionUz: "O'quv, ilmiy va badiiy adabiyotlar bazasini shakllantirish hamda kitobxonlarga xizmat ko'rsatish.",
    descriptionRu: "Формирование базы учебной, научной и художественной литературы, обслуживание читателей.",
    descriptionEn: "Forming educational, scientific, and literature database, providing library services to readers.",
    iconName: "FileText"
  },
  {
    nameUz: "Kengash",
    nameRu: "Совет",
    nameEn: "Council",
    descriptionUz: "Institut ilmiy Kengashi majlislarini tashkil etish va Kengash qarorlari ijrosini ta'minlash.",
    descriptionRu: "Организация заседаний Ученого совета института и обеспечение исполнения решений Совета.",
    descriptionEn: "Organization of Scientific Council meetings of the institute and ensuring implementation of Council decisions.",
    iconName: "Landmark"
  },
  {
    nameUz: "Korrupsiya qarshi kurashish \"Komplayens-nazorat\" tizimini boshqarish",
    nameRu: "Отдел управления системой «Комплаенс-контроль» по борьбе с коррупцией",
    nameEn: "Anti-Corruption \"Compliance Control\" System Management Department",
    descriptionUz: "Korrupsiyaviy omillarning oldini olish va komplayens-nazorat tizimini samarali boshqarish.",
    descriptionRu: "Предотвращение коррупционных факторов и эффективное управление системой комплаенс-контроля.",
    descriptionEn: "Preventing corruptive factors and effectively managing compliance control system.",
    iconName: "ShieldCheck"
  },
  {
    nameUz: "Marketing va talabalar amaliyoti \"Karyera markazi\"",
    nameRu: "Центр маркетинга и студенческой практики «Карьера»",
    nameEn: "Marketing and Student Internship \"Career Center\"",
    descriptionUz: "Bitiruvchilarni ish bilan ta'minlash, bandlikka ko'maklashish va talabalar amaliyotini tashkil qilish.",
    descriptionRu: "Содействие в трудоустройстве выпускников и организация студенческой практики.",
    descriptionEn: "Assisting in graduate employment and organizing student internships.",
    iconName: "TrendingUp"
  },
  {
    nameUz: "Xotin-qizlar kengashi",
    nameRu: "Совет женщин",
    nameEn: "Women's Council",
    descriptionUz: "Xotin-qizlar ijtimoiy-siyosiy faolligini oshirish hamda ularning huquq va manfaatlarini qo'llab-quvvatlash.",
    descriptionRu: "Повышение социально-политической активности женщин, поддержка их прав и интересов.",
    descriptionEn: "Increasing socio-political activity of women and supporting their rights and interests.",
    iconName: "Heart"
  },
  {
    nameUz: "Registrator ofisi",
    nameRu: "Офис регистратора",
    nameEn: "Registrar's Office",
    descriptionUz: "Talabalarning akademik ko'rsatkichlari, reytingi hamda o'zlashtirish hujjatlarini tizimli yuritish.",
    descriptionRu: "Систематическое ведение академических показателей, рейтингов и документов успеваемости студентов.",
    descriptionEn: "Systematic maintenance of students' academic performance records, grades, and ratings.",
    iconName: "Briefcase"
  },
  {
    nameUz: "O'qitishning texnik vositalari bo'limi",
    nameRu: "Отдел технических средств обучения",
    nameEn: "Technical Learning Media Department",
    descriptionUz: "Auditoriya va o'quv xonalarini zamonaviy axborot va multimediya texnikalari bilan ta'minlash va ularga xizmat ko'rsatish.",
    descriptionRu: "Оснащение и обслуживание аудиторий современной информационной и мультимедийной техникой.",
    descriptionEn: "Equipping and maintaining classrooms with modern information and multimedia technology.",
    iconName: "Cpu"
  }
];

export const renderCenterIcon = (iconName, className = "w-10 h-10 text-blue-500") => {
  switch (iconName) {
    case 'BookOpen': return <BookOpen className={className} />;
    case 'GraduationCap': return <GraduationCap className={className} />;
    case 'Library': return <Library className={className} />;
    case 'Users': return <Users className={className} />;
    case 'Award': return <Award className={className} />;
    case 'Heart': return <Heart className={className} />;
    case 'Sparkles': return <Sparkles className={className} />;
    case 'Star': return <Star className={className} />;
    case 'Scale': return <Scale className={className} />;
    case 'Globe': return <Globe className={className} />;
    case 'Shield': return <Shield className={className} />;
    case 'Monitor': return <Monitor className={className} />;
    case 'Calculator': return <Calculator className={className} />;
    case 'FileText': return <FileText className={className} />;
    case 'Landmark': return <Landmark className={className} />;
    case 'ShieldCheck': return <ShieldCheck className={className} />;
    case 'TrendingUp': return <TrendingUp className={className} />;
    case 'UserCheck': return <UserCheck className={className} />;
    case 'Briefcase': return <Briefcase className={className} />;
    case 'Cpu': return <Cpu className={className} />;
    case 'Stethoscope': return <Stethoscope className={className} />;
    case 'Microscope': return <Microscope className={className} />;
    case 'FolderCheck': return <FolderCheck className={className} />;
    case 'Trophy': return <Trophy className={className} />;
    case 'Camera': return <Camera className={className} />;
    case 'Megaphone': return <Megaphone className={className} />;
    case 'Zap': return <Zap className={className} />;
    case 'Compass': return <Compass className={className} />;
    case 'Flame': return <Flame className={className} />;
    case 'Target': return <Target className={className} />;
    case 'Coins': return <Coins className={className} />;
    case 'Database': return <Database className={className} />;
    case 'Printer': return <Printer className={className} />;
    case 'Radio': return <Radio className={className} />;
    case 'Lock': return <Lock className={className} />;
    case 'Wrench': return <Wrench className={className} />;
    case 'Truck': return <Truck className={className} />;
    case 'PieChart': return <PieChart className={className} />;
    case 'Layers': return <Layers className={className} />;
    case 'Building2': return <Building2 className={className} />;
    default: return <Building className={className} />;
  }
};

export const getAutoIcon = (title = '') => {
  const t = String(title).toLowerCase();

  if (t.includes('komplayens') || t.includes('korrupsiya')) return 'ShieldCheck';
  if (t.includes('sifatini') || t.includes('sifat')) return 'PieChart';
  if (t.includes('murojaat')) return 'Users';
  if (t.includes('kasaba')) return 'Shield';
  if (t.includes('raqamli') || t.includes('it markaz')) return 'Monitor';
  if (t.includes('texnik vosita') || t.includes('texnik vositalari')) return 'Cpu';
  if (t.includes('axborot resurs') || t.includes('arm')) return 'FileText';
  if (t.includes('uslubiy')) return 'Library';
  if (t.includes('xalqaro')) return 'Globe';
  if (t.includes('yoshlar') || t.includes('ma\'naviyat') || t.includes('ma`naviyat')) return 'Sparkles';
  if (t.includes('magistratura')) return 'Award';
  if (t.includes('iqtidorli')) return 'Star';
  if (t.includes('huquq') || t.includes('yurist')) return 'Scale';
  if (t.includes('marketing') || t.includes('karyera')) return 'TrendingUp';
  if (t.includes('buxgalter') || t.includes('moliya')) return 'Calculator';
  if (t.includes('xotin') || t.includes('qizlar')) return 'Heart';
  if (t.includes('xodimlar') || t.includes('kadr')) return 'UserCheck';
  if (t.includes('registrator')) return 'Briefcase';
  if (t.includes('kengash')) return 'Landmark';
  if (t.includes('ilmiy') || t.includes('tadqiqot') || t.includes('pedagog')) return 'GraduationCap';
  if (t.includes('o\'quv') || t.includes('ta\'lim')) return 'BookOpen';
  if (t.includes('tibbiyot') || t.includes('shifokor')) return 'Stethoscope';
  if (t.includes('laboratoriya')) return 'Microscope';
  if (t.includes('sport')) return 'Trophy';

  return 'Building';
};

export const getStoredCenters = () => {
  return initialCenters;
};

export const saveStoredCenters = (centers) => {
  // No-op to eliminate localStorage domain persistence
};




